package com.klima7.services.expert.features.jobs

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.platform.BaseViewModel

class JobsViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowNewTab: Event()
        object ShowRejectedTab: Event()
    }

    enum class Tab { New, Rejected }

    val selectedTab = MutableLiveData(Tab.New)

    fun newTabSelected() {
        sendEvent(Event.ShowNewTab)
        selectedTab.value = Tab.New
    }

    fun rejectedTabSelected() {
        sendEvent(Event.ShowRejectedTab)
        selectedTab.value = Tab.Rejected
    }

}