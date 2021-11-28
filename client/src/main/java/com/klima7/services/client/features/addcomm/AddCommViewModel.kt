package com.klima7.services.client.features.addcomm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.extensions.nullifyBlank
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import com.klima7.services.common.platform.BaseViewModel

class AddCommViewModel(
    private val getOfferWithExpertUC: GetOfferWithExpertUC,
    private val addRatingUC: AddRatingUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowAddCommentFailure(val failure: Failure): Event()
        object ShowCommentAddedMessage: Event()
    }

    private lateinit var offerId: String
    val offer = MutableLiveData<Offer>()
    val expert = MutableLiveData<Expert>()
    val profileImage = expert.map { it.profileImage }
    val name = expert.map { it.info.name }
    val serviceName = offer.map { it.serviceName }

    val rating = MutableLiveData(-1.0f)
    val comment = MutableLiveData("")

    val addButtonActive = rating.map { it >= 0 }

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun start(offerId: String) {
        this.offerId = offerId
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun addCommentClicked() {
        addCurrentRating()
    }

    fun retryAddCommentClicked() {
        addCurrentRating()
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getOfferWithExpertUC.start(
            viewModelScope,
            GetOfferWithExpertUC.Params(offerId),
            { failure ->
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

    private fun addCurrentRating() {
        val rating = this.rating.value?.toDouble()
        val comment = this.comment.value

        if(rating != null && rating >= 0) {
            addRating(rating, comment)
        }
    }

    private fun addRating(rating: Double, comment: String?) {
        loadState.value = LoadAreaView.State.PENDING
        addRatingUC.start(
            viewModelScope,
            AddRatingUC.Params(offerId, rating, comment.nullifyBlank()),
            { failure ->
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowAddCommentFailure(failure))
            },
            {
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowCommentAddedMessage)
                sendEvent(BaseEvent.FinishActivity)
            }
        )
    }

}