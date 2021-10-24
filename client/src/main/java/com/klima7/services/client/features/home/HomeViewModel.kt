package com.klima7.services.client.features.home

import com.klima7.services.common.platform.BaseViewModel

class HomeViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowSettingsScreen: Event()
    }

    fun settingsIconClicked() {
        sendEvent(Event.ShowSettingsScreen)
    }

}