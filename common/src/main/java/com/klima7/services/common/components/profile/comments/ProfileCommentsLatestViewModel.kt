package com.klima7.services.common.components.profile.comments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.comments.GetRatingsForExpertUC
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseLoadViewModel

class ProfileCommentsLatestViewModel(
    private val getRatingsForExpertUC: GetRatingsForExpertUC
): BaseLoadViewModel() {

    companion object {
        const val MAX_COMMENTS_COUNT = 3
    }

    private var expertUid: String? = null
    val ratings = MutableLiveData<List<Rating>>()

    fun setExpertUid(expertUid: String) {
        this.expertUid = expertUid
        this.expertUid?.let { uid ->
            loadRatings(uid)
        }
    }

    override fun refresh() {
        super.refresh()
        this.expertUid?.let { uid ->
            loadRatings(uid)
        }
    }

    private fun loadRatings(expertUid: String) {
        showLoading()
        getRatingsForExpertUC.start(
            viewModelScope,
            GetRatingsForExpertUC.Params(expertUid, null, MAX_COMMENTS_COUNT),
            { failure ->
                Log.i("abc", "Failure $failure")
                showFailure(failure)
            },
            { ratings ->
                Log.i("abc", "Success $ratings")
                this.ratings.value = ratings
                showMain()
            }
        )
    }
}