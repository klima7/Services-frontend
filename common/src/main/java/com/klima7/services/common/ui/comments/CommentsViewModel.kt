package com.klima7.services.common.ui.comments

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val ratingsRepository: RatingsRepository
): BaseViewModel() {

    fun start() {
        Log.i("Hello", "Comments VM started")
        viewModelScope.launch {
            ratingsRepository
                .getRatingsForExpert("GVUPHpMgt36NtVg9vsClFSaaOQQ7", "rating69", 10)
                .foldS({ failure ->
                    Log.i("Hello", "getRatingsForExpert failure: $failure")
                },
                { ratings ->
                    Log.i("Hello", "getRatingsForExpert success: $ratings")
                })
        }
    }

}