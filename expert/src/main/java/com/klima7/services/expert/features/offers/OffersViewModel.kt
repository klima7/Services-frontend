package com.klima7.services.expert.features.offers

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.platform.BaseViewModel

class OffersViewModel(
    private val offersRepository: OffersRepository
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowCurrentTab: Event()
        object ShowArchiveTab: Event()
    }

    enum class Tab { Current, Archive }

    val selectedTab = MutableLiveData(Tab.Current)
    val refreshEnabled = MutableLiveData(true)

    fun currentTabSelected() {
        sendEvent(Event.ShowCurrentTab)
        selectedTab.value = Tab.Current
    }

    fun archiveTabSelected() {
        sendEvent(Event.ShowArchiveTab)
        selectedTab.value = Tab.Archive
    }

    fun setRefreshEnabled(isEnabled: Boolean) {
        refreshEnabled.value = isEnabled
    }

}