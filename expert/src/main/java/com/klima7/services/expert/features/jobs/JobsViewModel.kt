package com.klima7.services.expert.features.jobs

import com.klima7.services.common.platform.BaseViewModel

class JobsViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowNewTab: Event()
        object ShowRejectedTab: Event()
    }

    fun newTabSelected() {
        sendEvent(Event.ShowNewTab)
    }

    fun rejectedTabSelected() {
        sendEvent(Event.ShowRejectedTab)
    }

}