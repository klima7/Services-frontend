package com.klima7.services.common.components.profile.ratings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.ratings.GetRatingsForExpertUC
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseViewModel

class ProfileRatingsViewModel(
    private val getRatingsForExpertUC: GetRatingsForExpertUC,
): BaseViewModel() {

    companion object {
        const val MAX_RATINGS_COUNT = 3
    }

    val expert = MutableLiveData<Expert>()
    val showMoreVisible = expert.map { expert -> expert.ratingsCount != 0 }
    val ratings = MutableLiveData<List<Rating>>()

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    sealed class Event: BaseEvent() {
        data class ShowRatingsScreen(val expertUid: String, val expertName: String): Event()
    }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
        loadRatings(expert.uid)
    }

    fun showAllRatingsClicked() {
        val uid = expert.value?.uid
        val name = expert.value?.info?.name
        if(uid != null && name != null)
        sendEvent(Event.ShowRatingsScreen(uid, name))
    }

    fun refresh() {
        val expertUid = expert.value?.uid
        if(expertUid != null) {
            loadRatings(expertUid)
        }
    }

    private fun loadRatings(expertUid: String) {
        loadState.value = LoadAreaView.State.LOAD
        getRatingsForExpertUC.start(
            viewModelScope,
            GetRatingsForExpertUC.Params(expertUid, null,
                MAX_RATINGS_COUNT
            ),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { ratings ->
                loadState.value = LoadAreaView.State.MAIN
                this.ratings.value = ratings
            }
        )
    }

}