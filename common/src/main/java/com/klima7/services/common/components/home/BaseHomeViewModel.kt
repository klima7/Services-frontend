package com.klima7.services.common.components.home

import com.klima7.services.common.platform.BaseViewModel

abstract class BaseHomeViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowSettingsScreen: Event()
        data class ShowOfferScreen(val offerId: String): Event()
    }

    fun start(offerId: String?) {
        if(offerId != null) {
            sendEvent(Event.ShowOfferScreen(offerId))
        }
    }

    fun settingsIconClicked() {
        sendEvent(Event.ShowSettingsScreen)
    }

}