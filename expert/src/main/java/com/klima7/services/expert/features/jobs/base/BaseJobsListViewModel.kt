package com.klima7.services.expert.features.jobs.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

abstract class BaseJobsListViewModel(
    private val getJobsIdsUC: BaseGetJobsIdsUC,
    private val getCurrentExpertJobsUC: GetCurrentExpertJobsUC,
): BaseViewModel() {

    private val allJobsIds = MutableLiveData<List<String>>()
    private val visibleJobsIds = MutableLiveData<Set<String>>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    @ExperimentalCoroutinesApi
    val pagingDataFlow = allJobsIds.asFlow().flatMapLatest { list -> createPager(list).flow }
        .cachedIn(viewModelScope)
        .combine(visibleJobsIds.asFlow()) { pagingData, visibleList ->
            pagingData.filter { expertJob -> visibleList.contains(expertJob.job.id) }
        }

    fun start() {
        getIds()
    }

    fun refresh() {
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
        loadState.value = LoadAreaView.State.LOAD
        getJobsIdsUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { ids ->
                allJobsIds.value = ids
                visibleJobsIds.value = ids.toMutableSet()
                loadState.value = LoadAreaView.State.MAIN
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