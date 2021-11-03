package com.klima7.services.expert.features.offers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.launch

class OffersViewModel(
    private val offersRepository: OffersRepository
): BaseViewModel() {

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