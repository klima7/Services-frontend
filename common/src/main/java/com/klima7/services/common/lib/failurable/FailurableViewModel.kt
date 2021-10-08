package com.klima7.services.common.lib.failurable

import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel

abstract class FailurableViewModel: BaseViewModel() {

    abstract fun refresh()

    sealed class FailurableEvent: BaseEvent() {
        data class ShowFailureEvent(val failure: Failure): FailurableEvent()
        object ShowMainEvent: FailurableEvent()
    }

    fun showFailure(failure: Failure) {
        sendEvent(FailurableEvent.ShowFailureEvent(failure))
    }

    fun showMain() {
        sendEvent(FailurableEvent.ShowMainEvent)
    }

}