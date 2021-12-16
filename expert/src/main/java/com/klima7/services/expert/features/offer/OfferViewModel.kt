package com.klima7.services.expert.features.offer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.offer.BaseOfferViewModel
import com.klima7.services.common.components.offer.GetOfferStreamUC
import com.klima7.services.common.components.offer.SetOfferStatusUC
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.expert.usecases.SetOfferArchivedUC

class OfferViewModel(
    setOfferStatusUC: SetOfferStatusUC,
    getOfferStreamUC: GetOfferStreamUC,
    private val getClientUC: GetClientUC,
    private val setOfferArchivedUC: SetOfferArchivedUC
): BaseOfferViewModel(setOfferStatusUC, getOfferStreamUC) {

    sealed class Event: BaseEvent() {
        data class ShowJobScreen(val jobId: String): Event()
        data class ShowArchiveFailureDialog(val failure: Failure): Event()
    }

    private var lastArchive: Boolean? = null

    override val offerStatus = offer.map { it?.status ?: OfferStatus.NEW }
    private val client = MutableLiveData<Client?>()

    val clientActive = offer.map { it == null || it.clientId != null }

    val clientName = offer.map { it?.clientName }
    val serviceName = offer.map { it?.serviceName }

    val callItemVisible = client.map { it?.info?.phone != null }
    val showRatingItemVisible = offer.map { it?.ratingId != null }
    val archiveItemVisible = offer.map { it?.archived ?: false }
    val unarchiveItemVisible = offer.map { it?.archived ?: false }

    fun callClientClicked() {
        val phone = client.value?.info?.phone
        if(phone != null) {
            sendEvent(BaseOfferViewModel.Event.Call(phone))
        }
    }

    fun showJobClicked() {
        val jobId = offer.value?.jobId
        if(jobId != null) {
            sendEvent(Event.ShowJobScreen(jobId))
        }
    }

    fun archiveOfferClicked() {
        setOfferArchived(offerId, true)
        lastArchive = true
    }

    fun unarchiveOfferClicked() {
        setOfferArchived(offerId, false)
        lastArchive = false
    }

    fun retryArchive() {
        val lastArchive = this.lastArchive
        if(lastArchive != null) {
            setOfferArchived(offerId, lastArchive)
        }
    }

    override fun onOfferUpdated(offer: Offer) {
        this.offer.value = offer
        if(client.value == null) {
            val clientId = offer.clientId
            if(clientId == null) {
                this.client.value = null
            }
            else {
                getClient(clientId)
            }
        }
    }

    private fun getClient(clientId: String) {
        getClientUC.start(
            viewModelScope,
            GetClientUC.Params(clientId),
            { },
            { client ->
                this.client.value = client
            }
        )
    }

    private fun setOfferArchived(offerId: String, archived: Boolean) {
        setOfferArchivedUC.start(
            viewModelScope,
            SetOfferArchivedUC.Params(offerId, archived),
            { failure ->
                sendEvent(Event.ShowArchiveFailureDialog(failure))
            },
            { }
        )
    }

}