package com.klima7.services.expert.features.offer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.offer.BaseOfferViewModel
import com.klima7.services.common.components.offer.GetOfferStreamUC
import com.klima7.services.common.components.offer.SetOfferStatusUC
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Offer

class OfferViewModel(
    setOfferStatusUC: SetOfferStatusUC,
    getOfferStreamUC: GetOfferStreamUC,
    private val getClientUC: GetClientUC,
): BaseOfferViewModel(setOfferStatusUC, getOfferStreamUC) {

    override val offer = MutableLiveData<Offer>()
    override val offerStatus = offer.map { it.status }
    private val client = MutableLiveData<Client>()
    val clientName = client.map { it.info.name }
    val serviceName = offer.map { it.serviceName }

    init {
        Log.i("Hello", "Log test")
    }

    override fun onOfferUpdated(offer: Offer) {
        Log.i("Hello", "Offer updated: $offer")
        this.offer.value = offer
        if(client.value == null) {
            getClient(offer.clientId)
        }
    }

    private fun getClient(clientId: String) {
        Log.i("Hello", "Getting client")
        getClientUC.start(
            viewModelScope,
            GetClientUC.Params(clientId),
            {
                Log.i("Hello", "Get client failure: $it")
                // TODO: Do something
            },
            { client ->
                Log.i("Hello", "Get client success: $client")
                this.client.value = client
            }
        )
    }

}