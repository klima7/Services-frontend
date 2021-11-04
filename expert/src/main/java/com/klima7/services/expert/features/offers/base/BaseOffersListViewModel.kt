package com.klima7.services.expert.features.offers.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

abstract class BaseOffersListViewModel(
    private val getOffersForCurrentExpertUC: GetOffersForCurrentExpertUC,
    private val moveOfferUC: MoveOfferUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object Refresh: Event()
    }

    private val hiddenOffersIds = MutableLiveData(setOf<String>())
    private val pager = createPager()
    val pagingDataFlow = pager.flow
        .cachedIn(viewModelScope)
        .combine(hiddenOffersIds.asFlow()) { pagingData, hiddenList ->
        pagingData.filter { offer -> !hiddenList.contains(offer.id) }
    }

    private fun createPager() = Pager(
        PagingConfig(pageSize = 5)
    ) {
        OffersPagingSource(getOffersForCurrentExpertUC)
    }

    fun refresh() {
        sendEvent(Event.Refresh)
    }

    fun offerSwiped(offerId: String) {
        hideOffer(offerId)
    }

    private fun hideOffer(offerId: String) {
        val cHiddenOffersIds = hiddenOffersIds.value
        if(cHiddenOffersIds != null) {
            val newHiddenOffersIds = cHiddenOffersIds.toMutableSet()
            newHiddenOffersIds.add(offerId)
            hiddenOffersIds.value = newHiddenOffersIds
        }
    }

    private fun showOffer(offerId: String) {
        val cHiddenOffersIds = hiddenOffersIds.value
        if(cHiddenOffersIds != null) {
            val newHiddenOffersIds = cHiddenOffersIds.toMutableSet()
            newHiddenOffersIds.remove(offerId)
            hiddenOffersIds.value = newHiddenOffersIds
        }
    }
}