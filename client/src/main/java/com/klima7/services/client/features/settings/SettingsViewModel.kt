package com.klima7.services.client.features.settings

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.usecases.SignOutUC

class SettingsViewModel(
    private val signOutUC: SignOutUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowInfoScreen: Event()
        object ShowSplashScreen: Event()
        object ShowDeleteScreen: Event()
        object ShowCreditsScreen: Event()
    }

    fun profileClicked() {
        sendEvent(Event.ShowInfoScreen)
    }

    fun deleteClicked() {
        sendEvent(Event.ShowDeleteScreen)
    }

    fun creditsClicked() {
        sendEvent(Event.ShowCreditsScreen)
    }

    fun signOutClicked() {
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