package com.klima7.services.common.components.offer

import android.util.Log
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseOfferViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowOfferStatusChangeEnsureDialog(val newOfferStatus: OfferStatus): Event()
    }

    fun offerStatusSelected(newOfferStatus: OfferStatus) {
        Log.i("Hello", "Offer status selected: $newOfferStatus")
        sendEvent(Event.ShowOfferStatusChangeEnsureDialog(newOfferStatus))
    }

    fun changeOfferStatusConfirmed() {
        Log.i("Hello", "changeOfferStatusConfirmed")
    }

}