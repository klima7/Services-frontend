package com.klima7.services.common.components.home

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.login.RefreshTokenUC
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseHomeViewModel(
    private val refreshTokenUC: RefreshTokenUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowSettingsScreen: Event()
        data class ShowOfferScreen(val offerId: String): Event()
    }

    fun start(offerId: String?) {
        refreshToken()
        if(offerId != null) {
            sendEvent(Event.ShowOfferScreen(offerId))
        }
    }

    fun settingsIconClicked() {
        sendEvent(Event.ShowSettingsScreen)
    }

    private fun refreshToken() {
        refreshTokenUC.start(
            viewModelScope,
            None(),
            {
            },
            {
            }
        )
    }

}