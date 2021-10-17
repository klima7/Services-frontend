package com.klima7.services.expert.features.settings

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.usecases.SignOutUC
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.ui.base.BaseViewModel

class SettingsViewModel(
    private val signOutUC: SignOutUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowLocationScreen: Event()
        object ShowSplashScreen: Event()
    }

    fun profileClicked() {
        sendEvent(Event.ShowInfoScreen)
    }

    fun servicesClicked() {
        sendEvent(Event.ShowServicesScreen)
    }

    fun locationClicked() {
        sendEvent(Event.ShowLocationScreen)
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