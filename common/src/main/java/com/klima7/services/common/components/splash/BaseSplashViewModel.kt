package com.klima7.services.common.components.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.UserState
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseSplashViewModel(
    private val getCurrentUserStateUC: GetCurrentUserStateUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowLoginScreen: Event()
        data class ShowHomeScreen(val offerId: String?): Event()
        object ShowSetupScreen: Event()
    }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    open fun started(offerId: String?) {
        if(offerId != null) {
            proceed(offerId)
        }
        else {
            viewModelScope.launch {
                delay(2000)
                proceed()
            }
        }
    }

    fun refresh() {
        proceed()
    }

    fun loginActivityFinished() {
        proceed()
    }

    private fun proceed(offerId: String? = null) {
        getCurrentUserStateUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            }, { userState ->
                loadState.value = LoadAreaView.State.MAIN
                when(userState) {
                    UserState.NOT_LOGGED -> sendEvent(Event.ShowLoginScreen)
                    UserState.READY -> sendEvent(Event.ShowHomeScreen(offerId))
                    UserState.NOT_READY -> sendEvent(Event.ShowSetupScreen)
                }
            }
        )
    }

}
