package com.klima7.services.expert.features.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.failurable.FailurableViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): FailurableViewModel() {

    sealed class Event: BaseEvent() {
        object ShowLoginScreen: Event()
        object ShowHomeScreen: Event()
        object ShowSetupScreen: Event()
    }

    fun splashStarted() {
        viewModelScope.launch {
            delay(1000)
            goToNextScreen()
        }
    }

    fun loginActivityFinished() {
        viewModelScope.launch {
            goToNextScreen()
        }
    }

    override fun refresh() {
        viewModelScope.launch {
            goToNextScreen()
        }
    }

    private suspend fun goToNextScreen() {
        checkAuthenticatedPart()
    }

    private suspend fun checkAuthenticatedPart() {
        authRepository.getUid().foldS({
        }, { uid ->
            val authenticated = uid != null
            if(!authenticated) {
                sendEvent(Event.ShowLoginScreen)
            } else {
                getExpertPart(uid!!)
            }
        })
    }

    private suspend fun getExpertPart(uid: String) {
        expertsRepository.getExpert(uid).foldS({ failure ->
            when(failure) {
                Failure.NotFoundFailure -> createExpertPart(uid)
                else -> notifyFailure(failure)
            }
        }, { expert -> checkExpertReadyPart(expert) })
    }

    private suspend fun createExpertPart(uid: String) {
        expertsRepository.createExpertAccount().foldS({ failure ->
            notifyFailure(failure)
        }, {
            getExpertPart(uid)
        })
    }

    private fun checkExpertReadyPart(expert: Expert) {
        if(isExpertReady(expert)) {
            sendEvent(Event.ShowHomeScreen)
        }
        else if(!expert.fromCache) {
            sendEvent(Event.ShowSetupScreen)
        }
        else {
            notifyFailure(Failure.InternetFailure)
        }
    }

    private fun isExpertReady(expert: Expert): Boolean {
        return expert.info.name != null && expert.area != null && expert.services.isNotEmpty()
    }

    private fun notifyFailure(failure: Failure) {
        showFailure(failure)
    }

}
