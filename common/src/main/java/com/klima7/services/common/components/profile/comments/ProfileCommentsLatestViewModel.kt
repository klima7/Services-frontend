package com.klima7.services.common.components.profile.comments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.components.comments.GetRatingsForExpertUC

class ProfileCommentsLatestViewModel(
    private val getRatingsForExpertUC: GetRatingsForExpertUC
): BaseViewModel() {

    companion object {
        const val MAX_COMMENTS_COUNT = 3
    }

    val ratings = MutableLiveData<List<Rating>>()

    fun setExpertUid(expertUid: String) {
        getRatingsForExpertUC.start(
            viewModelScope,
            GetRatingsForExpertUC.Params(expertUid, null, MAX_COMMENTS_COUNT),
            { failure ->
                Log.i("Hello", "Failure occured $failure")
            },
            { ratings ->
                this.ratings.value = ratings
            }
        )
    }

}