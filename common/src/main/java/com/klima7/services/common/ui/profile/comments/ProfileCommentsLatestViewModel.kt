package com.klima7.services.common.ui.profile.comments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.comments.GetExpertRatingsWithProfileImagesUC
import com.klima7.services.common.ui.rating.RatingWithProfileImage

class ProfileCommentsLatestViewModel(
    private val getExpertRatingsWithProfileImagesUC: GetExpertRatingsWithProfileImagesUC
): BaseViewModel() {

    companion object {
        const val MAX_COMMENTS_COUNT = 3
    }

    val rwpis = MutableLiveData<List<RatingWithProfileImage>>()

    fun setExpertUid(expertUid: String) {
        getExpertRatingsWithProfileImagesUC.start(
            viewModelScope,
            GetExpertRatingsWithProfileImagesUC.Params(expertUid, null, MAX_COMMENTS_COUNT),
            { failure ->
                Log.i("Hello", "Failure occured $failure")
            },
            { rwpis ->
                this.rwpis.value = rwpis
            }
        )
    }

}