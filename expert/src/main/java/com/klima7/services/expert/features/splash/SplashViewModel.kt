package com.klima7.services.expert.features.splash

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.expert.ExpertNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository,
    private val navigator: ExpertNavigator
): BaseViewModel() {

    val refreshVisible = MutableLiveData(false)
    val refreshEnabled = MutableLiveData(true)

    sealed class Event: BaseEvent() {
        object ShowLoginScreen: Event()
    }

    fun goToNextScreen(activity: Activity) {
        viewModelScope.launch {
            goToNextScreenSuspend(activity)
        }
    }

    private suspend fun goToNextScreenSuspend(activity: Activity) {
        checkAuthenticatedPart(activity)
    }

    private suspend fun checkAuthenticatedPart(activity: Activity) {
        disableRefresh()
        authRepository.getUid().foldS({
            Log.i("Hello", "Error occurred")
        }, { uid ->
            val authenticated = uid != null
            if(!authenticated) {
                sendEvent(Event.ShowLoginScreen)
            } else {
                Log.i("Hello", "Not go to login")
                getExpertPart(activity, uid!!)
            }
        })
    }

    private suspend fun getExpertPart(activity: Activity, uid: String) {
        expertsRepository.getExpert(uid).foldS({ failure ->
            when(failure) {
                Failure.ExpertNotFoundFailure -> createExpertPart(activity, uid)
                Failure.InternetFailure -> enableRefresh()
                else -> Log.i("Hello", "Unknown error while getExpert occurred $failure")
            }
        }, { expert -> checkExpertReadyPart(activity, expert) })
    }

    private suspend fun createExpertPart(activity: Activity, uid: String) {
        expertsRepository.createExpertAccount().foldS({ failure ->
            Log.e("Hello", "failure during createExpertAccount")
            enableRefresh()
        }, {
            Log.i("Hello", "createExpertAccount success")
            getExpertPart(activity, uid)
        })
    }

    private suspend fun checkExpertReadyPart(activity: Activity, expert: Expert) {
        if(isExpertReady(expert)) {
            navigator.showHomeScreen(activity)
        }
        else if(!expert.fromCache) {
            navigator.showSetupScreen(activity)
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
