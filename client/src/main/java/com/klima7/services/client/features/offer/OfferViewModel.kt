package com.klima7.services.client.features.offer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.offer.BaseOfferViewModel
import com.klima7.services.common.components.offer.SetOfferStatusUC
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.usecases.GetExpertUC
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OfferViewModel(
    setOfferStatusUC: SetOfferStatusUC,
    private val getExpertUC: GetExpertUC,
    private val getOfferStreamUC: GetOfferStreamUC,
): BaseOfferViewModel(setOfferStatusUC) {

    sealed class Event: BaseEvent() {
        data class ShowAddCommentScreen(val offerId: String): Event()
        data class ShowCommentScreen(val ratingId: String): Event()
        data class ShowExpertProfileScreen(val expertUid: String): Event()
        data class Call(val phoneNumber: String): Event()
    }

    private lateinit var offerId: String
    private val expert = MutableLiveData<Expert>()
    override val offer = MutableLiveData<Offer>()
    override val offerStatus = offer.map { it.status }

    val rateVisible = offer.map { it.ratingId  == null }
    val showRatingVisible = offer.map { it.ratingId  != null }
    val expertName = expert.map { it.info.name }
    val serviceName = offer.map { it.serviceName }

    fun start(offerId: String) {
        this.offerId = offerId
        startOfferStream(offerId)
    }

    fun addCommentClicked() {
        sendEvent(Event.ShowAddCommentScreen(offerId))
    }

    fun showCommentClicked() {
        val ratingId = offer.value?.ratingId
        if(ratingId != null) {
            sendEvent(Event.ShowCommentScreen(ratingId))
        }
    }

    fun callExpertClicked() {
        val phone = expert.value?.info?.phone
        if(phone != null) {
            sendEvent(Event.Call(phone))
        }
    }

    fun showExpertProfileClicked() {
        val cExpert = this.expert.value
        if(cExpert != null) {
            sendEvent(Event.ShowExpertProfileScreen(cExpert.uid))
        }
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

    private fun onOfferUpdated(offer: Offer) {
        this.offer.value = offer
        if(expert.value == null) {
            getExpert(offer.expertId)
        }
    }

    private fun getExpert(expertId: String) {
        getExpertUC.start(
            viewModelScope,
            GetExpertUC.Params(expertId),
            {
                // TODO: Do something
            },
            { expert ->
                this.expert.value = expert
            }
        )
    }

}