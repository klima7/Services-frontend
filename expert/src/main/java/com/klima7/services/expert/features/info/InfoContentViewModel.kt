package com.klima7.services.expert.features.info

import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.ExpertInfo
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.failurable.FailurableViewModel
import com.klima7.services.common.lib.utils.CombinedLiveData
import com.klima7.services.common.lib.utils.nullifyBlank
import kotlinx.coroutines.launch

class InfoContentViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): FailurableViewModel() {

    val name = MutableLiveData("")
    val company = MutableLiveData("")
    val description = MutableLiveData("")
    val phone = MutableLiveData("")
    val email = MutableLiveData("")
    val website = MutableLiveData("")

    val avatar = MutableLiveData("")

    val nameError = Transformations.map(name) {
        if (it.nullifyBlank() == null) NameError.NotProvided else null
    }

    val phoneError = Transformations.map(phone) {
        if (it.isEmpty() || it.length == 9) null else PhoneError.TooShort
    }

    val emailError = Transformations.map(email) { email ->
        if (email.isEmpty() || isEmailValid(email)) null else EmailError.InvalidFormat
    }

    val websiteError = Transformations.map(website) { website ->
        if (website.isEmpty() || isWebsiteAddressValid(website)) null else WebsiteError.InvalidFormat
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
    }

    fun infoStarted() {
        updateViews()
    }

    fun saveClicked() {
        saveInfo()
        saveProfileImage()
    }

    fun changeProfileImageClicked() {
        sendEvent(Event.StartProfileImagePicker)
    }

    fun profileImageSelected(uri: String) {
        avatar.value = uri
    }

    override fun refresh() {
        updateViews()
    }

    private fun updateViews() {
        viewModelScope.launch {
            getExpertPart()
        }
    }

    private suspend fun getExpertPart() {
        authRepository.getUid().foldS({ failure ->
            notifyFailure(failure)
        }, { uid ->
            if(uid == null) {
                notifyFailure(Failure.UnknownFailure)
            }
            else {
                expertsRepository.getExpert(uid).foldS({ failure ->
                    notifyFailure(failure)
                }, { expert ->
                    setFields(expert)
                    setProfileImage(expert)
                    showMain()
                })
            }
        })
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
        viewModelScope.launch {
            expertsRepository.getProfileImage(expert.uid).foldS({
            }, {
                avatar.postValue(it)
            })
        }
    }

    private fun notifyFailure(failure: Failure) {
        showFailure(failure)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun isWebsiteAddressValid(websiteAddress: String): Boolean {
        return URLUtil.isValidUrl(websiteAddress)
    }

    private fun saveInfo() {
        loadingVisible.value = true

        val info = ExpertInfo(
            name.value.nullifyBlank(),
            company.value.nullifyBlank(),
            description.value.nullifyBlank(),
            phone.value.nullifyBlank(),
            email.value.nullifyBlank(),
            website.value.nullifyBlank()
        )

        viewModelScope.launch {
            expertsRepository.setExpertInfo(info).foldS({ failure ->
                loadingVisible.value = false
            }, {
                loadingVisible.value = false
                sendEvent(Event.FinishInfo)
            })
        }
    }

    private fun saveProfileImage() {
        viewModelScope.launch {
            expertsRepository.setProfileImage(avatar.value!!).foldS({}, {}) // TODO
        }
    }
}