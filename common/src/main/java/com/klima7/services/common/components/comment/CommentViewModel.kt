package com.klima7.services.common.components.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseViewModel

class CommentViewModel(
    private val getRatingUC: GetRatingUC
): BaseViewModel() {

    lateinit var ratingId: String
    val rating = MutableLiveData<Rating>()

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
        getRatingUC.start(
            viewModelScope,
            GetRatingUC.Params(ratingId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { rating ->
                this.rating.value = rating
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}