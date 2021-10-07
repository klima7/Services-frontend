package com.klima7.services.expert.features.info

import android.util.Log
import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.failurable.FailurableViewModel
import com.klima7.services.common.lib.utils.CombinedLiveData
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
        if (it.isNotEmpty()) null else NameError.NotProvided
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
        Log.i("Hello", "Someting has changed: $nameError, $phoneError, $emailError, $websiteError, ($enabled)")
        enabled
    }

    fun infoStarted() {
        setExpertData()
    }

    fun saveClicked() {
        Log.i("Hello", "Save clicked")
    }

    override fun refresh() {
        setExpertData()
    }

    private fun setExpertData() {
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
}