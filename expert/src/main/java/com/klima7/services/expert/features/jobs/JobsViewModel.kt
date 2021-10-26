package com.klima7.services.expert.features.jobs

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseLoadViewModel
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class JobsViewModel(
    private val getAvailableJobsIdsUC: GetAvailableJobsIdsUC,
    private val getJobsUC: GetJobsUC,
): BaseLoadViewModel() {

    private val jobsIds = MutableLiveData<List<String>>()
    val visibleJobsIds = MutableLiveData<List<String>>()

    val flow = jobsIds.asFlow().flatMapLatest { list -> createPager(list).flow }
        .cachedIn(viewModelScope)
        .combine(visibleJobsIds.asFlow()) { pagingData, visibleList ->
            pagingData.filter { job ->
                val result = visibleList.contains(job.id)
                Log.i("Hello", "Checking ${job.id} -> $result")
                result
            }
        }

    fun start() {
        showMain()
        getIds()
    }

    private fun getIds() {
        getAvailableJobsIdsUC.start(
            viewModelScope,
            None(),
            { failure ->
                Log.i("Hello", "getIds failure: $failure")
            },
            { ids ->
                jobsIds.value = ids
                visibleJobsIds.value = ids.toMutableList()
            }
        )
    }

    private fun createPager(jobsIds: List<String>) = Pager(
        PagingConfig(pageSize = 5)
    ) {
        JobsPagingSource(jobsIds, getJobsUC)
    }

    fun hideId(id: String) {
        val newList = visibleJobsIds.value?.toMutableList()
        newList?.remove(id)
        visibleJobsIds.value = newList!!
    }

    fun fade() {
        viewModelScope.launch {
            showPending()
            delay(1000)
            showMain()
        }
    }

}