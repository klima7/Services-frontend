package com.klima7.services.common.ui.comments

import androidx.lifecycle.*
import androidx.paging.*
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.ui.base.BaseViewModel

class CommentsViewModel(
    private val getExpertRatingsWithProfileImagesUC: GetExpertRatingsWithProfileImagesUC
): BaseViewModel() {

    private val expertId = MutableLiveData<String>()
    private val pager = expertId.map { expertId -> createPager(expertId) }
    val pagingData = pager.switchMap { pager -> pager.flow.cachedIn(viewModelScope).asLiveData() }

    fun start(expertId: String) {
        this.expertId.value = expertId
    }

    private fun createPager(expertId: String) = Pager(
        PagingConfig(pageSize = 5)
    ) {
        CommentsPagingSource(getExpertRatingsWithProfileImagesUC, expertId)
    }

}