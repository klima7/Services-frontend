package com.klima7.services.expert.features.jobs.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseLoadViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

abstract class BaseJobsListViewModel(
    private val getJobsIdsUC: BaseGetJobsIdsUC,
    private val getCurrentExpertJobsUC: GetCurrentExpertJobsUC,
): BaseLoadViewModel() {

    private val allJobsIds = MutableLiveData<List<String>>()
    private val visibleJobsIds = MutableLiveData<Set<String>>()

    @ExperimentalCoroutinesApi
    val pagingDataFlow = allJobsIds.asFlow().flatMapLatest { list -> createPager(list).flow }
        .cachedIn(viewModelScope)
        .combine(visibleJobsIds.asFlow()) { pagingData, visibleList ->
            pagingData.filter { expertJob -> visibleList.contains(expertJob.job.id) }
        }

    fun start() {
        getIds()
    }

    override fun refresh() {
        getIds()
    }

    fun jobChanged(jobId: String) {
        hideJob(jobId)
    }

    private fun createPager(jobsIds: List<String>) = Pager(
        PagingConfig(pageSize = 5)
    ) {
        JobsPagingSource(jobsIds, getCurrentExpertJobsUC)
    }

    private fun getIds() {
        showLoading()
        getJobsIdsUC.start(
            viewModelScope,
            None(),
            { failure ->
                showFailure(failure)
            },
            { ids ->
                allJobsIds.value = ids
                visibleJobsIds.value = ids.toMutableSet()
                showMain()
            }
        )
    }

    protected fun hideJob(jobId: String) {
        val cVisibleJobsIds = visibleJobsIds.value
        if(cVisibleJobsIds != null) {
            val newVisibleJobsIds = cVisibleJobsIds.toMutableSet()
            newVisibleJobsIds.remove(jobId)
            visibleJobsIds.value = newVisibleJobsIds
        }
    }

    protected fun showJob(jobId: String) {
        val cVisibleJobsIds = visibleJobsIds.value
        if(cVisibleJobsIds != null) {
            val newVisibleJobsIds = cVisibleJobsIds.toMutableSet()
            newVisibleJobsIds.add(jobId)
            visibleJobsIds.value = newVisibleJobsIds
        }
    }

}