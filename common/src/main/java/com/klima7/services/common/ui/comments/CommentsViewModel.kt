package com.klima7.services.common.ui.comments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import org.w3c.dom.Comment

class CommentsViewModel(
    private val ratingsRepository: RatingsRepository
): BaseViewModel() {

//    private val source = CommentsPagingSource(ratingsRepository)

    private val pager = MutableLiveData<Pager<String, Comment>>()
    val pagingData = pager.switchMap { pager -> pager.liveData }

    fun start(expertId: String) {
        Log.i("Hello", "Comments VM started")
        viewModelScope.launch {
            ratingsRepository
                .getRatingsForExpert(expertId, "rating69", 10)
                .foldS({ failure ->
                    Log.i("Hello", "getRatingsForExpert failure: $failure")
                },
                { ratings ->
                    Log.i("Hello", "getRatingsForExpert success: $ratings")
                })
        }
    }

}