package com.klima7.services.expert.features.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): BaseViewModel() {

    val refreshVisible = MutableLiveData(false)
    val refreshEnabled = MutableLiveData(true)

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

    fun refreshClicked() {
        viewModelScope.launch {
            disableRefresh()
            goToNextScreen()
        }
    }

    fun loginActivityFinished() {
        viewModelScope.launch {
            goToNextScreen()
        }
    }

    private suspend fun goToNextScreen() {
        checkAuthenticatedPart()
    }

    private suspend fun checkAuthenticatedPart() {
        disableRefresh()
        authRepository.getUid().foldS({
            Log.i("Hello", "Error occurred")
        }, { uid ->
            val authenticated = uid != null
            if(!authenticated) {
                sendEvent(Event.ShowLoginScreen)
            } else {
                Log.i("Hello", "Not go to login")
                getExpertPart(uid!!)
            }
        })
    }

    private suspend fun getExpertPart(uid: String) {
        expertsRepository.getExpert(uid).foldS({ failure ->
            when(failure) {
                Failure.NotFoundFailure -> createExpertPart(uid)
                Failure.InternetFailure -> enableRefresh()
                else -> Log.i("Hello", "Unknown error while getExpert occurred $failure")
            }
        }, { expert -> checkExpertReadyPart(expert) })
    }

    private suspend fun createExpertPart(uid: String) {
        expertsRepository.createExpertAccount().foldS({ failure ->
            Log.e("Hello", "failure during createExpertAccount")
            enableRefresh()
        }, {
            Log.i("Hello", "createExpertAccount success")
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
            enableRefresh()
        }
    }

    private fun isExpertReady(expert: Expert): Boolean {
        return expert.info.name != null && expert.area != null && expert.services.isNotEmpty()
    }

    private fun enableRefresh() {
        refreshVisible.value = true
        refreshEnabled.value = true
    }

    private fun disableRefresh() {
        refreshEnabled.value = false
    }

}
