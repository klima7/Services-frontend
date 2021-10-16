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
import com.klima7.services.common.domain.models.ProfileImage
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome
import com.klima7.services.common.ui.loadable.LoadableViewModel
import com.klima7.services.common.ui.utils.CombinedLiveData
import com.klima7.services.common.ui.utils.nullifyBlank
import kotlinx.coroutines.launch

class InfoContentViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): LoadableViewModel() {

    private var avatarUriToSave: String? = null

    val name = MutableLiveData("")
    val company = MutableLiveData("")
    val description = MutableLiveData("")
    val phone = MutableLiveData("")
    val email = MutableLiveData("")
    val website = MutableLiveData("")

    val avatar = MutableLiveData<ProfileImage?>()

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
        object ShowSaveError: Event()
    }

    fun started() {
        updateViews()
    }

    fun saveClicked() {
        viewModelScope.launch {
            saveInfoAndProfile()
        }
    }

    fun changeProfileImageClicked() {
        sendEvent(Event.StartProfileImagePicker)
    }

    fun profileImageSelected(uri: String) {
        avatarUriToSave = uri
        avatar.value = ProfileImage(uri, 0)
    }

    override fun refresh() {
        updateViews()
    }

    private fun updateViews() {
        showLoading()
        viewModelScope.launch {
            getExpertAndUpdateViews()
        }
    }

    private suspend fun getExpertAndUpdateViews() {
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
        avatar.value = expert.profileImage // TODO: Tidy
//        viewModelScope.launch {
//            expertsRepository.getProfileImage(expert.uid).foldS({
//                avatar.postValue("")
//            }, {
//                avatar.postValue(it)
//            })
//        }
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

    private suspend fun saveInfoAndProfile() {
        showPending()
        val infoResult = saveInfo()
        val profileResult = saveProfileImage()
        if(infoResult.isFailure || profileResult.isFailure) {
            sendEvent(Event.ShowSaveError)
        }
        else {
            sendEvent(Event.FinishInfo)
        }
        showMain()
    }

    private suspend fun saveInfo(): Outcome<Failure, None> {
        val info = ExpertInfo(
            name.value.nullifyBlank(),
            company.value.nullifyBlank(),
            description.value.nullifyBlank(),
            phone.value.nullifyBlank(),
            email.value.nullifyBlank(),
            website.value.nullifyBlank()
        )

        return expertsRepository.setExpertInfo(info)
    }

    private suspend fun saveProfileImage(): Outcome<Failure, None> {
        val avatarUriToSaveConst = avatarUriToSave
        return if(avatarUriToSaveConst != null)
            expertsRepository.setProfileImage(avatarUriToSaveConst)
        else
            Outcome.Success(None())

    }
}