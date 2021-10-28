package com.klima7.services.expert.features.jobs

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseLoadViewModel
import com.klima7.services.expert.usecases.RejectJobUC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class JobsViewModel(
    private val getAvailableJobsIdsUC: GetAvailableJobsIdsUC,
    private val getJobsUC: GetJobsUC,
    private val rejectJobUC: RejectJobUC,
): BaseLoadViewModel() {

    sealed class Event: BaseEvent() {
        object ShowRejectFailure: Event()
    }

    private val allJobsIds = MutableLiveData<List<String>>()
    private val visibleJobsIds = MutableLiveData<Set<String>>()

    @ExperimentalCoroutinesApi
    val pagingDataFlow = allJobsIds.asFlow().flatMapLatest { list -> createPager(list).flow }
        .cachedIn(viewModelScope)
        .combine(visibleJobsIds.asFlow()) { pagingData, visibleList ->
            pagingData.filter { job ->
                val result = visibleList.contains(job.id)
                Log.i("Hello", "Checking ${job.id} -> $result")
                result
            }
        }

    fun start() {
        getIds()
    }

    override fun refresh() {
        getIds()
    }

    fun jobSwiped(id: String) {
        rejectJob(id)
    }

    private fun createPager(jobsIds: List<String>) = Pager(
        PagingConfig(pageSize = 5)
    ) {
        JobsPagingSource(jobsIds, getJobsUC)
    }

    private fun getIds() {
        showLoading()
        getAvailableJobsIdsUC.start(
            viewModelScope,
            None(),
            { failure ->
                Log.i("Hello", "getIds failure: $failure")
                showFailure(failure)
            },
            { ids ->
                allJobsIds.value = ids
                visibleJobsIds.value = ids.toMutableSet()
                showMain()
            }
        )
    }

    private fun rejectJob(jobId: String) {
        hideJob(jobId);
        showPending()
        rejectJobUC.start(
            viewModelScope,
            RejectJobUC.Params(jobId),
            {
                showMain()
                showJob(jobId)
                sendEvent(Event.ShowRejectFailure);
            },
            {
                showMain()
            }
        )
    }

    private fun hideJob(jobId: String) {
        val cVisibleJobsIds = visibleJobsIds.value
        if(cVisibleJobsIds != null) {
            val newVisibleJobsIds = cVisibleJobsIds.toMutableSet()
            newVisibleJobsIds.remove(jobId)
            visibleJobsIds.value = newVisibleJobsIds
        }
    }

    private fun showJob(jobId: String) {
        val cVisibleJobsIds = visibleJobsIds.value
        if(cVisibleJobsIds != null) {
            val newVisibleJobsIds = cVisibleJobsIds.toMutableSet()
            newVisibleJobsIds.add(jobId)
            visibleJobsIds.value = newVisibleJobsIds
        }
    }

}