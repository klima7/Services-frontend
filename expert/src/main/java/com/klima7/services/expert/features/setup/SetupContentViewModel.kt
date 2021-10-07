package com.klima7.services.expert.features.setup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.failurable.FailurableViewModel
import kotlinx.coroutines.launch

class SetupContentViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): FailurableViewModel() {

    val profileConfigured = MutableLiveData(false)
    val servicesConfigured = MutableLiveData(false)
    val locationConfigured = MutableLiveData(false)

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowLocationScreen: Event()
    }

    fun setupStarted() {
        updateSetupState()
    }

    fun continueClicked() {

    }

    fun infoClicked() {
        sendEvent(Event.ShowInfoScreen)
    }

    fun servicesClicked() {
        sendEvent(Event.ShowServicesScreen)
    }

    fun locationClicked() {
        sendEvent(Event.ShowLocationScreen)
    }

    fun configDone() {
        updateSetupState()
    }

    override fun refresh() {
        updateSetupState()
    }

    private fun updateSetupState() {
        viewModelScope.launch {
            getUidPart()
        }
    }

    private suspend fun getUidPart() {
        authRepository.getUid().foldS({ failure ->
            notifyFailure(failure)
        }, { uid ->
            if(uid == null) {
                notifyFailure(Failure.UnknownFailure)
            }
            else {
                getExpertPart(uid)
            }
        })
    }

    private suspend fun getExpertPart(uid: String) {
        expertsRepository.getExpert(uid).foldS({ failure ->
            notifyFailure(failure)
        }, { expert ->
            if(expert.fromCache) {
                notifyFailure(Failure.InternetFailure)
            }
            else {
                verifyExpertPart(expert)
                showMain()
            }
        })
    }

    private fun verifyExpertPart(expert: Expert) {
        profileConfigured.value = expert.info.name != null
        servicesConfigured.value = expert.services.isNotEmpty()
        locationConfigured.value = expert.area != null
    }

    private fun notifyFailure(failure: Failure) {
        showFailure(failure)
    }
}