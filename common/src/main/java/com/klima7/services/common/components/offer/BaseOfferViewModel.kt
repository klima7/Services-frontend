package com.klima7.services.common.components.offer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseOfferViewModel(
    private val setOfferStatusUC: SetOfferStatusUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowOfferStatusChangeEnsureDialog(val newOfferStatus: OfferStatus): Event()
    }

    protected abstract val offer: MutableLiveData<Offer>
    abstract val offerStatus: LiveData<OfferStatus>
    private var newOfferStatus: OfferStatus? = null

    fun offerStatusSelected(newOfferStatus: OfferStatus) {
        Log.i("Hello", "Offer status selected: $newOfferStatus")
        this.newOfferStatus = newOfferStatus
        sendEvent(Event.ShowOfferStatusChangeEnsureDialog(newOfferStatus))
    }

    fun changeOfferStatusConfirmed() {
        Log.i("Hello", "Status change confirmed")
        val offerId = this.offer.value?.id
        val offerStatus = this.newOfferStatus
        if(offerId != null && offerStatus != null) {
            Log.i("Hello", "Non null")
            setOfferStatus(offerId, offerStatus)
        }
    }

    private fun setOfferStatus(offerId: String, offerStatus: OfferStatus) {
        Log.i("Hello", "setOfferStatus")
        setOfferStatusUC.start(
            viewModelScope,
            SetOfferStatusUC.Params(offerId, offerStatus),
            { failure ->
                Log.i("Hello", "Failure ocurred: $failure")
            },
            {
                Log.i("Hello", "Success")
            }
        )
    }

}