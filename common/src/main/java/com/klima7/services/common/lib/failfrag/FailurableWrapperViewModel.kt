package com.klima7.services.common.lib.failfrag

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel


class FailurableWrapperViewModel: BaseViewModel() {

    val pendingRefresh = MutableLiveData(false)
    val currentFailure = MutableLiveData<Failure?>(null)

    sealed class Event: BaseEvent() {
        object RefreshMainFragment: Event()
        object StartShowMainAnimation: Event()
        object StartShowFailureAnimation: Event()
    }

    fun showFailure(failure: Failure) {
        Log.i("Hello", "showFailure in VM")
        if(currentFailure.value == null)
            sendEvent(Event.StartShowFailureAnimation)
        pendingRefresh.value = false
        currentFailure.value = failure
    }

    fun showMain() {
        Log.i("Hello", "showMain in VM")
        if(currentFailure.value != null)
            sendEvent(Event.StartShowMainAnimation)
        pendingRefresh.value = false
        currentFailure.value = null
    }

    fun refreshClicked() {
        pendingRefresh.value?.let { value ->
            if(!value) {
                sendEvent(Event.RefreshMainFragment)
                pendingRefresh.value = true
            }
        }

    }

}