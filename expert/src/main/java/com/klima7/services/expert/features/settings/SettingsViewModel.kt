package com.klima7.services.expert.features.settings

import android.util.Log
import com.klima7.services.common.lib.base.BaseViewModel

class SettingsViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowLocationScreen: Event()
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

    fun logoutClicked() {
        Log.i("Hello", "Logout clicked")
    }

}