package com.klima7.services.expert.features.settings

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.usecases.SignOutUC
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseViewModel

class SettingsViewModel(
    private val signOutUC: SignOutUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowWorkingAreaScreen: Event()
        object ShowSplashScreen: Event()
    }

    fun profileClicked() {
        sendEvent(Event.ShowInfoScreen)
    }

    fun servicesClicked() {
        sendEvent(Event.ShowServicesScreen)
    }

    fun locationClicked() {
        sendEvent(Event.ShowWorkingAreaScreen)
    }

    fun signOutClicked() {
        signOut()
    }

    private fun signOut() {
        signOutUC.start(
            viewModelScope,
            None(),
            {
                // Do nothing - in current implementation sign out failure is impossible
            }, {
                sendEvent(Event.ShowSplashScreen)
            }
        )
    }

}