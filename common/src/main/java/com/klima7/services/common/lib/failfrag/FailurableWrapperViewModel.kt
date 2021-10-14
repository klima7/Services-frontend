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

    private val state = MutableLiveData(State.Loading)
    val currentFailure = MutableLiveData<Failure?>(null)
    val pendingRefresh = MutableLiveData(false)

    val interactionEnabled = state.map { state -> state == State.Main }

    data class ViewState(val mainAlpha: Float, val failureAlpha: Float, val spinnerAlpha: Float)

    enum class State {
        Main,
        Failure,
        Loading,
        Pending
    }

    val viewState = state.map { state ->
        val main = when(state) {
            State.Main -> 1.0f
            State.Pending -> 0.5f
            else -> 0.0f
        }
        val failure = if(state == State.Failure) 1.0f else 0.0f
        val spinner = if(state == State.Loading || state == State.Pending) 1.0f else 0.0f
        ViewState(main, failure, spinner)
    }

    sealed class Event: BaseEvent() {
        object RefreshMainFragment: Event()
    }

    fun showFailure(failure: Failure) {
        Log.i("Hello", "showFailure in VM")
        pendingRefresh.value = false
        currentFailure.value = failure
        state.value = State.Failure
    }

    fun showMain() {
        Log.i("Hello", "showMain in VM")
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