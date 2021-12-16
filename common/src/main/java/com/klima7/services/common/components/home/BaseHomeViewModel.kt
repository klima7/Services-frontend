package com.klima7.services.common.components.home

import com.klima7.services.common.platform.BaseViewModel

abstract class BaseHomeViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowSettingsScreen: Event()
    }

    fun settingsIconClicked() {
        sendEvent(Event.ShowSettingsScreen)
    }

}