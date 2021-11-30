package com.klima7.services.common.components.offer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Failure
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
        data class ShowOfferStatusChangeFailureDialog(val failure: Failure): Event()
        data class Call(val phoneNumber: String): Event()
        data class ShowCommentScreen(val ratingId: String): Event()
    }

    protected lateinit var offerId: String
    private var newOfferStatus: OfferStatus? = null

    protected abstract val offer: MutableLiveData<Offer>
    abstract val offerStatus: LiveData<OfferStatus>

    fun start(offerId: String) {
        this.offerId = offerId
        startOfferStream(offerId)
    }

    fun showRatingClicked() {
        val ratingId = offer.value?.ratingId
        if(ratingId != null) {
            sendEvent(Event.ShowCommentScreen(ratingId))
        }
    }

    fun offerStatusSelected(newOfferStatus: OfferStatus) {
        this.newOfferStatus = newOfferStatus
        sendEvent(Event.ShowOfferStatusChangeEnsureDialog(newOfferStatus))
    }

    fun retryChangeOfferStatus() {
        changeOfferStatus()
    }

    fun changeOfferStatusConfirmed() {
        changeOfferStatus()
    }

    private fun changeOfferStatus() {
        val offerId = this.offer.value?.id
        val offerStatus = this.newOfferStatus
        if(offerId != null && offerStatus != null) {
            setOfferStatus(offerId, offerStatus)
        }
    }

    private fun setOfferStatus(offerId: String, offerStatus: OfferStatus) {
        setOfferStatusUC.start(
            viewModelScope,
            SetOfferStatusUC.Params(offerId, offerStatus),
            { failure ->
                sendEvent(Event.ShowOfferStatusChangeFailureDialog(failure))
            },
            { }
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