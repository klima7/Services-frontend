package com.klima7.services.expert.features.home

import com.klima7.services.common.ui.base.BaseViewModel

class HomeViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowSettingsScreen: Event()
    }

    fun settingsIconClicked() {
        sendEvent(Event.ShowSettingsScreen)
    }

}