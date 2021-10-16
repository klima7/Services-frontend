package com.klima7.services.expert.features.settings

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.ui.base.BaseViewModel

class SettingsViewModel(
    private val authRepository: AuthRepository
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
        authRepository.signOut()
        sendEvent(Event.ShowSplashScreen)
    }

}