package com.klima7.services.client.features.jobs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.flow.combine

class JobsViewModel(
    private val getCurrentClientJobsUC: GetCurrentClientJobsUC,
    private val getAllServicesUC: GetAllServicesUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowOffersScreen(val jobId: String): Event()
        object RefreshJobs: Event()
    }

    val services = MutableLiveData<List<Service>>()
    val visibleServicesIds = MutableLiveData<Set<String>>(emptySet())

    private val pager = createPager()
    val pagingDataFlow = pager.flow
        .cachedIn(viewModelScope)
        .combine(visibleServicesIds.asFlow()) { pagingData, visibleList ->
            pagingData.filter { offer -> visibleList.isEmpty() || visibleList.contains(offer.serviceId) }
        }

    fun start() {
        getServices()
    }

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

    private fun getServices() {
        getAllServicesUC.start(
            viewModelScope,
            None(),
            { },
            { services ->
                this.services.value = services
            }
        )
    }

}