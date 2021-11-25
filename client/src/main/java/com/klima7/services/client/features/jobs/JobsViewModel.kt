package com.klima7.services.client.features.jobs

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.klima7.services.common.platform.BaseViewModel

class JobsViewModel(
    private val getCurrentClientJobsUC: GetCurrentClientJobsUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowOffersScreen(val jobId: String): Event()
        object RefreshJobs: Event()
    }

    private val pager = createPager()
    val pagingDataFlow = pager.flow.cachedIn(viewModelScope)

    fun refresh() {
        sendEvent(Event.RefreshJobs)
    }

    fun jobClicked(jobId: String) {
        sendEvent(Event.ShowOffersScreen(jobId))
    }

    private fun createPager() = Pager(
        PagingConfig(pageSize = 5)
    ) {
        JobsPagingSource(getCurrentClientJobsUC)
    }

}