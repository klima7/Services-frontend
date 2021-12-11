package com.klima7.services.client.features.offer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.offer.BaseOfferViewModel
import com.klima7.services.common.components.offer.GetOfferStreamUC
import com.klima7.services.common.components.offer.SetOfferStatusUC
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Offer
import com.klima7.services.common.usecases.GetExpertUC

class OfferViewModel(
    setOfferStatusUC: SetOfferStatusUC,
    getOfferStreamUC: GetOfferStreamUC,
    private val getExpertUC: GetExpertUC,
): BaseOfferViewModel(setOfferStatusUC, getOfferStreamUC) {

    sealed class Event: BaseEvent() {
        data class ShowAddCommentScreen(val offerId: String): Event()
        data class ShowExpertProfileScreen(val expertUid: String): Event()
    }

    override val offer = MutableLiveData<Offer>()
    override val offerStatus = offer.map { it.status }
    private val expert = MutableLiveData<Expert?>()

    val addRatingItemVisible = offer.map { it.ratingId  == null }
    val showRatingItemVisible = offer.map { it.ratingId  != null }
    val callItemVisible = expert.map { it?.info?.phone != null }
    val expertName = offer.map { it.expertName }
    val serviceName = offer.map { it.serviceName }

    fun addCommentClicked() {
        sendEvent(Event.ShowAddCommentScreen(offerId))
    }

    fun callExpertClicked() {
        val phone = expert.value?.info?.phone
        if(phone != null) {
            sendEvent(BaseOfferViewModel.Event.Call(phone))
        }
    }

    fun showExpertProfileClicked() {
        val cExpert = this.expert.value
        if(cExpert != null) {
            sendEvent(Event.ShowExpertProfileScreen(cExpert.uid))
        }
    }

    override fun onOfferUpdated(offer: Offer) {
        this.offer.value = offer
        if(expert.value == null) {
            val expertId = offer.expertId
            if(expertId == null) {
                this.expert.value = null
            }
            else {
                getExpert(expertId)
            }
        }
    }

    private fun getExpert(expertId: String) {
        getExpertUC.start(
            viewModelScope,
            GetExpertUC.Params(expertId),
            { },
            { expert ->
                this.expert.value = expert
            }
        )
    }

}