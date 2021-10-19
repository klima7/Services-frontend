package com.klima7.services.expert.features.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.UserState
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.ui.base.BaseLoadViewModel
import com.klima7.services.common.ui.loadable.LoadableViewModel
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
