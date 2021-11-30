package com.klima7.services.expert.features.offers.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.usecases.SetOfferArchivedUC
import kotlinx.coroutines.flow.combine

abstract class BaseOffersListViewModel(
    private val getOffersForCurrentExpertUC: GetOffersForCurrentExpertUC,
    private val setOfferArchivedUC: SetOfferArchivedUC,
    private val shouldArchive: Boolean,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object Refresh: Event()
        class ShowMoveFailure(val failure: Failure): Event()
    }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    private var lastMovedOfferId: String? = null

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
        hiddenOffersIds.value = setOf()
    }

    fun offerSwiped(offerId: String) {
        moveOffer(offerId)
    }

    private fun moveOffer(offerId: String) {
        lastMovedOfferId = offerId
        hideOffer(offerId)
        loadState.value = LoadAreaView.State.PENDING
        setOfferArchivedUC.start(
            viewModelScope,
            SetOfferArchivedUC.Params(offerId, shouldArchive),
            { failure ->
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowMoveFailure(failure))
                showOffer(offerId)
            },
            {
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

    fun retryMoveOffer() {
        lastMovedOfferId?.let { id ->
            moveOffer(id)
        }
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