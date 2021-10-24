package com.klima7.services.client.features.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.UserState
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseLoadViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getCurrentExpertStateUC: GetCurrentExpertStateUC
): BaseLoadViewModel() {

    sealed class Event: BaseEvent() {
        object ShowLoginScreen: Event()
        object ShowHomeScreen: Event()
        object ShowSetupScreen: Event()
    }

    fun started() {
        showMain()
        viewModelScope.launch {
            delay(2000)
            proceed()
        }
    }

    override fun refresh() {
        proceed()
    }

    fun loginActivityFinished() {
        proceed()
    }

    private fun proceed() {
        showPending()
        getCurrentExpertStateUC.start(
            viewModelScope,
            None(),
            { failure ->
                showFailure(failure)
            }, { userState ->
                showMain()
                when(userState) {
                    UserState.NOT_LOGGED -> sendEvent(Event.ShowLoginScreen)
                    UserState.READY -> sendEvent(Event.ShowHomeScreen)
                    UserState.NOT_READY -> sendEvent(Event.ShowSetupScreen)
                }
            }
        )
    }

}
