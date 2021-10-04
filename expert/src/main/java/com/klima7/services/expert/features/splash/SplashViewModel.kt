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
        delay(1000)
        authRepository.getUid().foldS({
            Log.i("Hello", "Error occurred")
        }, { uid ->
            val authenticated = uid != null
            if(!authenticated) {
                navigator.showLoginScreen(activity)
            } else {
                getExpertPart(activity, uid!!)
                Log.i("Hello", "Not go to login")
            }
        })
    }

    private suspend fun getExpertPart(activity: Activity, uid: String) {
        expertsRepository.getExpert(uid).fold({ failure ->
            when(failure) {
                Failure.ExpertNotFoundFailure -> enableRefresh()
                else -> Log.i("Hello", "Unknown error while getExpert occurred")
            }
        }, { expert ->
            if(isExpertReady(expert)) {
                navigator.showHomeScreen(activity)
            }
            else if(!expert.fromCache) {
                navigator.showSetupScreen(activity)
            }
            else {
                enableRefresh()
            }
        })
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
