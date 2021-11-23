package com.klima7.services.common.components.settings

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.usecases.SignOutUC

open class BaseSettingsViewModel(
    private val signOutUC: SignOutUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowSplashScreen: Event()
        object ShowCreditsScreen: Event()
        object ShowLogoutQuery: Event()
    }

    fun settingsOptionClicked(settingsOption: SettingsOption) {
        if(settingsOption.event == Event.ShowSplashScreen) {
            sendEvent(Event.ShowLogoutQuery)
        }
        else {
            sendEvent(settingsOption.event)
        }
    }

    fun logoutConfirmed() {
        signOut()
    }

    private fun signOut() {
        signOutUC.start(
            viewModelScope,
            None(),
            {},
            {
                sendEvent(Event.ShowSplashScreen)
            }
        )
    }

}