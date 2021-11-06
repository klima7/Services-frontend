package com.klima7.services.client.features.jobs

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.klima7.services.common.platform.BaseLoadViewModel

class JobsViewModel(
    private val getCurrentClientJobsUC: GetCurrentClientJobsUC
): BaseLoadViewModel() {

    private val pager = createPager()
    val pagingDataFlow = pager.flow.cachedIn(viewModelScope)

    override fun refresh() {
        // TODO: refresh
    }

    private fun createPager() = Pager(
        PagingConfig(pageSize = 5)
    ) {
        JobsPagingSource(getCurrentClientJobsUC)
    }

}