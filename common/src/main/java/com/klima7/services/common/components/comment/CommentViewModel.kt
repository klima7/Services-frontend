package com.klima7.services.common.components.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.RatingWithExpert
import com.klima7.services.common.platform.BaseViewModel

class CommentViewModel(
    private val getRatingWithExpertUC: GetRatingWithExpertUC,
): BaseViewModel() {

    lateinit var ratingId: String

    private val ratingWithExpert = MutableLiveData<RatingWithExpert>()
    val rating = ratingWithExpert.map { it.rating }
    val subtitle = ratingWithExpert.map { it.expert.info.name }

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun start(ratingId: String) {
        this.ratingId = ratingId
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getRatingWithExpertUC.start(
            viewModelScope,
            GetRatingWithExpertUC.Params(ratingId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { ratingWithExpert ->
                this.ratingWithExpert.value = ratingWithExpert
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }
}
