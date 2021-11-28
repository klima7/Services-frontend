package com.klima7.services.common.components.rating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseViewModel

class RatingViewModel(
    private val getRatingWithExpertUC: GetRatingWithExpertUC,
): BaseViewModel() {

    private lateinit var ratingId: String

    val rating = MutableLiveData<Rating>()
    val expert = MutableLiveData<Expert>()
    val subtitle = expert.map { it.info.name }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    fun start(ratingId: String, rating: Rating?) {
        this.ratingId = ratingId
        this.rating.value = rating
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.MAIN
        getRatingWithExpertUC.start(
            viewModelScope,
            GetRatingWithExpertUC.Params(ratingId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { ratingWithExpert ->
                this.expert.value = ratingWithExpert.expert
                this.rating.value = ratingWithExpert.rating
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }
}
