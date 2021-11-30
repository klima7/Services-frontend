package com.klima7.services.expert.features.offer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.offer.BaseOfferViewModel
import com.klima7.services.common.components.offer.GetOfferStreamUC
import com.klima7.services.common.components.offer.SetOfferStatusUC
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.Offer

class OfferViewModel(
    setOfferStatusUC: SetOfferStatusUC,
    getOfferStreamUC: GetOfferStreamUC,
    private val getClientUC: GetClientUC,
): BaseOfferViewModel(setOfferStatusUC, getOfferStreamUC) {

    sealed class Event: BaseEvent() {
        data class ShowJobScreen(val jobId: String): Event()
    }

    override val offer = MutableLiveData<Offer>()
    override val offerStatus = offer.map { it.status }
    private val client = MutableLiveData<Client>()

    val clientName = client.map { it.info.name }
    val serviceName = offer.map { it.serviceName }

    val callItemVisible = client.map { it.info.phone  != null }
    val showRatingItemVisible = offer.map { it.ratingId  != null }
    val archiveItemVisible = offer.map { !it.archived }
    val unarchiveItemVisible = offer.map { it.archived }

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

    override fun onOfferUpdated(offer: Offer) {
        this.offer.value = offer
        if(client.value == null) {
            getClient(offer.clientId)
        }
    }

    private fun getClient(clientId: String) {
        getClientUC.start(
            viewModelScope,
            GetClientUC.Params(clientId),
            {
                // TODO: Do something
            },
            { client ->
                this.client.value = client
            }
        )
    }

}