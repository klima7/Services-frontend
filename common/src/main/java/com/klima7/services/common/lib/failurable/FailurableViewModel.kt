package com.klima7.services.common.lib.failurable

import android.util.Log
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel

abstract class FailurableViewModel: BaseViewModel() {

    abstract fun refresh()

    data class ShowFailureEvent(val failure: Failure): BaseEvent()
    object ShowMainEvent: BaseEvent()

    fun showFailure(failure: Failure) {
        Log.i("Hello", "showFailure in FailurableViewModel fragment")
        sendEvent(ShowFailureEvent(failure))
    }

    fun showMain() {
        sendEvent(ShowMainEvent)
    }

}