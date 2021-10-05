package com.klima7.services.common.lib.fragments

import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel

abstract class FailurableViewModel: BaseViewModel() {

    abstract fun refresh()

    data class ShowFailureEvent(val failure: Failure): BaseEvent()

    fun showFailure(failure: Failure) {
        sendEvent(ShowFailureEvent(failure))
    }

}