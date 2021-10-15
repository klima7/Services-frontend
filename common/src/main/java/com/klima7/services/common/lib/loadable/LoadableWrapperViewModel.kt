package com.klima7.services.common.lib.loadable

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel


class LoadableWrapperViewModel: BaseViewModel() {

    private val state = MutableLiveData<State?>(null)
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
        object ShowRefreshButtonAnimation: Event()
    }

    fun showFailure(failure: Failure) {
        pendingRefresh.value = false
        currentFailure.value = failure
        state.value = State.Failure
    }

    fun showMain() {
        pendingRefresh.value = false
        currentFailure.value = null
        state.value = State.Main
    }

    fun showLoading() {
        state.value = State.Loading
    }

    fun showPending() {
        state.value = State.Pending
    }

    fun refreshClicked() {
        pendingRefresh.value?.let { value ->
            if(!value) {
                sendEvent(Event.RefreshMainFragment)
                sendEvent(Event.ShowRefreshButtonAnimation)
                pendingRefresh.value = true
            }
        }

    }

}