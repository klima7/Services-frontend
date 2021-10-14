package com.klima7.services.common.lib.failfrag

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FailurableWrapperViewModel: BaseViewModel() {

    // Input values
    val state = MutableLiveData(State.Main)
    val currentFailure = MutableLiveData<Failure?>(null)
    val pendingRefresh = MutableLiveData(false)

    val interactionEnabled = state.map { state -> state != State.Loading }
    val failureVisible = state.map { state -> state == State.Failure }
    val mainVisible = state.map { state -> state == State.Main }
    val spinnerVisible = state.map { state -> state == State.Loading || state == State.Pending }

    enum class State {
        Main,
        Failure,
        Loading,
        Pending
    }

    sealed class Event: BaseEvent() {
        object RefreshMainFragment: Event()
        object StartShowMainAnimation: Event()
        object StartShowFailureAnimation: Event()
    }

    fun showFailure(failure: Failure) {
        Log.i("Hello", "showFailure in VM")
//        if(currentFailure.value == null)
//            sendEvent(Event.StartShowFailureAnimation)
        pendingRefresh.value = false
        currentFailure.value = failure
        state.value = State.Failure
    }

    fun showMain() {
        Log.i("Hello", "showMain in VM")
//        if(currentFailure.value != null)
//            sendEvent(Event.StartShowMainAnimation)
        pendingRefresh.value = false
        currentFailure.value = null
        state.value = State.Main
    }

    fun showLoading() {
        Log.i("Hello", "showLoading in VM")
        state.value = State.Loading
    }

    fun showPending() {
        Log.i("Hello", "showPending in VM")
        state.value = State.Pending
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