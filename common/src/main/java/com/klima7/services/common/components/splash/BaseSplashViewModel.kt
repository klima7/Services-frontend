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
        data class ShowHomeScreen(val animate: Boolean): Event()
        object ShowSetupScreen: Event()
    }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    open fun started(offerId: String?) {
        if(offerId != null) {
            viewModelScope.launch {
                delay(500)
                proceed(false)
            }
        }
        else {
            viewModelScope.launch {
                delay(2000)
                proceed(true)
            }
        }
    }

    fun refresh() {
        proceed(true)
    }

    fun loginActivityFinished() {
        proceed(true)
    }

    private fun proceed(animate: Boolean) {
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
                    UserState.READY -> sendEvent(Event.ShowHomeScreen(animate))
                    UserState.NOT_READY -> sendEvent(Event.ShowSetupScreen)
                }
            }
        )
    }

}
