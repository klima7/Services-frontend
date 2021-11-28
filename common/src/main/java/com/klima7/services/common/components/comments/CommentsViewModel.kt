package com.klima7.services.common.components.comments

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.klima7.services.common.platform.BaseViewModel

class CommentsViewModel(
    private val getRatingsForExpertUC: GetRatingsForExpertUC
): BaseViewModel() {

    private val expertId = MutableLiveData<String>()
    val subtitle = MutableLiveData<String>()
    private val pager = expertId.map { expertId -> createPager(expertId) }
    val pagingData = pager.switchMap { pager -> pager.flow.cachedIn(viewModelScope).asLiveData() }

    fun start(expertId: String, expertName: String) {
        this.expertId.value = expertId
        this.subtitle.value = expertName
    }

    private fun createPager(expertId: String) = Pager(
        PagingConfig(pageSize = 5)
    ) {
        CommentsPagingSource(getRatingsForExpertUC, expertId)
    }

}