package com.klima7.services.expert.features.offers

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.platform.BaseViewModel

class OffersViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowCurrentTab: Event()
        object ShowArchiveTab: Event()
    }

    enum class Tab { Current, Archive }

    val selectedTab = MutableLiveData(Tab.Current)

    fun currentTabSelected() {
        sendEvent(Event.ShowCurrentTab)
        selectedTab.value = Tab.Current
    }

    fun archiveTabSelected() {
        sendEvent(Event.ShowArchiveTab)
        selectedTab.value = Tab.Archive
    }

}