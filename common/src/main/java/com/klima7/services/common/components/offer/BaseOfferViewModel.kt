package com.klima7.services.common.components.offer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.flow.collectLatest

abstract class BaseOfferViewModel(
    private val setOfferStatusUC: SetOfferStatusUC,
    private val getOfferStreamUC: GetOfferStreamUC,
): BaseViewModel() {

    open class Event: BaseEvent() {
        data class ShowOfferStatusChangeEnsureDialog(val newOfferStatus: OfferStatus): Event()
        data class Call(val phoneNumber: String): Event()
    }

    protected lateinit var offerId: String
    protected abstract val offer: MutableLiveData<Offer>
    abstract val offerStatus: LiveData<OfferStatus>
    private var newOfferStatus: OfferStatus? = null

    fun start(offerId: String) {
        this.offerId = offerId
        startOfferStream(offerId)
    }

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

    private fun startOfferStream(offerId: String) {
        getOfferStreamUC.start(
            viewModelScope,
            GetOfferStreamUC.Params(offerId),
            {},
            { stream ->
                stream.collectLatest(this::onOfferUpdated)
            }
        )
    }

    protected abstract fun onOfferUpdated(offer: Offer)

}