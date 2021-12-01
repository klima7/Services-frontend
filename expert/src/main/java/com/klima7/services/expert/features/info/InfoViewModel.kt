package com.klima7.services.expert.features.info

import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.extensions.nullifyBlank
import com.klima7.services.common.models.*
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.platform.CombinedLiveData
import com.klima7.services.expert.usecases.GetCurrentExpertUC

class InfoViewModel(
    private val getCurrentExpertUC: GetCurrentExpertUC,
    private val setCurrentExpertInfoAndImageUC: SetCurrentExpertInfoAndImageUC
): BaseViewModel() {

    private var profileImageUrlToSave = MutableLiveData<ProfileImageUrl?>(null)
    val expert = MutableLiveData<Expert?>(null)

    val name = MutableLiveData("")
    val company = MutableLiveData("")
    val description = MutableLiveData("")
    val phone = MutableLiveData("")
    val email = MutableLiveData("")
    val website = MutableLiveData("")

    val avatar = MutableLiveData<ProfileImage?>()

    val nameError = Transformations.map(name) {
        if (it.nullifyBlank() == null) InfoFormErrors.NameError.NotProvided else null
    }

    val phoneError = Transformations.map(phone) {
        if (it.isEmpty() || it.length == 9) null else InfoFormErrors.PhoneError.TooShort
    }

    val emailError = Transformations.map(email) { email ->
        if (email.isEmpty() || isEmailValid(email)) null else InfoFormErrors.EmailError.InvalidFormat
    }

    val websiteError = Transformations.map(website) { website ->
        if (website.isEmpty() || isWebsiteAddressValid(website)) null else InfoFormErrors.WebsiteError.InvalidFormat
    }

    val saveButtonEnabled = CombinedLiveData(nameError, phoneError, emailError, websiteError) {
        val (nameError, phoneError, emailError, websiteError) = it
        val enabled = nameError == null && phoneError == null && emailError == null && websiteError == null
        enabled
    }

    val restoreImageVisible = profileImageUrlToSave.map { it != null }
    val deleteImageVisible = CombinedLiveData(expert, restoreImageVisible) {
        expert.value?.profileImage != null && restoreImageVisible.value == false
    }

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    sealed class Event: BaseEvent() {
        object FinishInfo: Event()
        object StartProfileImagePicker: Event()
        data class ShowSaveFailure(val failure: Failure): Event()
    }

    fun started() {
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun saveClicked() {
        save()
    }

    fun retrySaveClicked() {
        save()
    }

    fun changeProfileImageClicked() {
        sendEvent(Event.StartProfileImagePicker)
    }

    fun deleteProfileImageClicked() {
        profileImageUrlToSave.value = ProfileImageUrl(null)
        avatar.value = null
    }

    fun restoreProfileImageClicked() {
        profileImageUrlToSave.value = null
        avatar.value = expert.value?.profileImage
    }

    fun profileImageSelected(url: String) {
        profileImageUrlToSave.value = ProfileImageUrl(url)
        avatar.value = ProfileImage(url)
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getCurrentExpertUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { expert ->
                this.expert.value = expert
                setFields(expert)
                setProfileImage(expert)
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

    private fun setFields(expert: Expert) {
        name.value = expert.info.name ?: ""
        company.value = expert.info.company ?: ""
        description.value = expert.info.description ?: ""
        phone.value = expert.info.phone ?: ""
        email.value = expert.info.email ?: ""
        website.value = expert.info.website ?: ""
    }

    private fun setProfileImage(expert: Expert) {
        avatar.value = expert.profileImage
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isWebsiteAddressValid(websiteAddress: String): Boolean {
        return URLUtil.isValidUrl(websiteAddress)
    }

    private fun save() {
        loadState.value = LoadAreaView.State.PENDING
        setCurrentExpertInfoAndImageUC.start(
            viewModelScope,
            SetCurrentExpertInfoAndImageUC.Params(
                ExpertInfo(
                    name.value.nullifyBlank(),
                    company.value.nullifyBlank(),
                    description.value.nullifyBlank(),
                    phone.value.nullifyBlank(),
                    email.value.nullifyBlank(),
                    website.value.nullifyBlank()
                ),
                profileImageUrlToSave.value
            ),
            { failure ->
                sendEvent(Event.ShowSaveFailure(failure))
                loadState.value = LoadAreaView.State.MAIN
            },
            {
                sendEvent(Event.FinishInfo)
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}