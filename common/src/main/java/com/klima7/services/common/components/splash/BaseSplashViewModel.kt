package com.klima7.services.common.components.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.UserState
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseLoadViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseSplashViewModel(
    private val getCurrentUserStateUC: GetCurrentUserStateUC
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
        getCurrentUserStateUC.start(
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
