package com.klima7.services.expert.features.info

import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.ExpertInfo
import com.klima7.services.common.domain.models.ProfileImage
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.ui.base.BaseLoadViewModel
import com.klima7.services.common.ui.utils.CombinedLiveData
import com.klima7.services.common.ui.utils.nullifyBlank
import com.klima7.services.expert.usecases.GetCurrentExpertUC

class InfoViewModel(
    private val getCurrentExpertUC: GetCurrentExpertUC,
    private val setCurrentExpertInfoAndImageUC: SetCurrentExpertInfoAndImageUC
): BaseLoadViewModel() {

    private var profileImageUrlToSave: String? = null

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

    val loadingVisible = MutableLiveData(false)

    sealed class Event: BaseEvent() {
        object FinishInfo: Event()
        object StartProfileImagePicker: Event()
        object ShowSaveError: Event()
    }

    fun started() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    fun saveClicked() {
        save()
    }

    fun changeProfileImageClicked() {
        sendEvent(Event.StartProfileImagePicker)
    }

    fun profileImageSelected(uri: String) {
        profileImageUrlToSave = uri
        avatar.value = ProfileImage(uri, 0)
    }

    private fun loadContent() {
        showLoading()
        getCurrentExpertUC.start(
            viewModelScope,
            None(),
            { failure ->
                showFailure(failure)
            },
            { expert ->
                setFields(expert)
                setProfileImage(expert)
                showMain()
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
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun isWebsiteAddressValid(websiteAddress: String): Boolean {
        return URLUtil.isValidUrl(websiteAddress)
    }

    private fun save() {
        showPending()
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
                profileImageUrlToSave
            ),
            {
                sendEvent(Event.ShowSaveError)
                showMain()
            },
            {
                sendEvent(Event.FinishInfo)
                showMain()
            }
        )
    }

}