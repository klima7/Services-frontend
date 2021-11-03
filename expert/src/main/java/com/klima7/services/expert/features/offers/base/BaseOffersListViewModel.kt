package com.klima7.services.expert.features.offers.base

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseOffersListViewModel(
    private val getOffersForCurrentExpertUC: GetOffersForCurrentExpertUC
): BaseViewModel() {

    private val pager = createPager()
    val pagingData = pager.flow.cachedIn(viewModelScope).asLiveData()

    private fun createPager() = Pager(
        PagingConfig(pageSize = 5)
    ) {
        OffersPagingSource(getOffersForCurrentExpertUC)
    }

    fun refresh() {

    }
}