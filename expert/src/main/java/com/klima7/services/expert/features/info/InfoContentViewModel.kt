package com.klima7.services.expert.features.info

import android.util.Log
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

    val nameError = Transformations.map(name) {
        Log.i("Hello", "name '$it'")
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
    }

    fun changeProfileImageClicked() {
        sendEvent(Event.StartProfileImagePicker)
    }

    fun profileImageSelected(uri: String) {
        Log.i("Hello", "Profile image selected and cropped $uri")

        viewModelScope.launch {
            expertsRepository.setProfileImage(uri).foldS({
                Log.i("Hello", "Profile image change failure ($it)")
            }, {
                Log.i("Hello", "Profile image change success")
            })
        }
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
        Log.i("Hello", "Saveing info")
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
                Log.w("Hello","saveInfo Failure $failure")
                loadingVisible.value = false
            }, {
                Log.i("Hello","saveInfo Success")
                loadingVisible.value = false
                sendEvent(Event.FinishInfo)
            })
        }
    }
}