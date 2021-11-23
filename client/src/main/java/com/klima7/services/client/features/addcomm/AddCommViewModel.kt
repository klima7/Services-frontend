package com.klima7.services.client.features.addcomm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import com.klima7.services.common.platform.BaseViewModel

class AddCommViewModel(
    private val getOfferWithExpertUC: GetOfferWithExpertUC,
    private val addRatingUC: AddRatingUC,
): BaseViewModel() {

    private lateinit var offerId: String
    val offer = MutableLiveData<Offer>()
    val expert = MutableLiveData<Expert>()
    val profileImage = expert.map { it.profileImage }
    val name = expert.map { it.info.name }
    val serviceName = offer.map { it.serviceName }
    val rating = MutableLiveData(0.0f)

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun start(offerId: String) {
        this.offerId = offerId
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getOfferWithExpertUC.start(
            viewModelScope,
            GetOfferWithExpertUC.Params(offerId),
            { failure ->
                Log.i("Hello", "AddComm failure")
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { offerWithExpert ->
                loadState.value = LoadAreaView.State.MAIN
                offer.value = offerWithExpert.offer
                expert.value = offerWithExpert.expert
            }
        )
    }

}