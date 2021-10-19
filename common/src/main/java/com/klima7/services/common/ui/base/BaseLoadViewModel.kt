package com.klima7.services.common.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.domain.models.Failure

open class BaseLoadViewModel: BaseViewModel() {

    enum class LoadState {
        Main,
        Failure,
        Loading,
        Pending
    }

    data class LoadViewState(
        val mainAlpha: Float,
        val failureAlpha: Float,
        val spinnerAlpha: Float
        )

    sealed class LoadEvent: BaseEvent() {
        object ShowRefreshButtonAnimation: LoadEvent()
    }

    private val state = MutableLiveData<LoadState?>(null)
    private val _currentFailure = MutableLiveData<Failure?>(null)
    private val _pendingRefresh = MutableLiveData(false)

    val loadFailure: LiveData<Failure?> = _currentFailure
    val loadPending: LiveData<Boolean> = _pendingRefresh
    val loadInteraction = state.map { state -> state == LoadState.Main }
    val loadViewState = state.map { state -> stateToViewState(state) }

    private fun stateToViewState(state: LoadState?): LoadViewState {
        val main = when(state) {
            LoadState.Main -> 1.0f
            LoadState.Pending -> 0.5f
            else -> 0.0f
        }
        val failure = if(state == LoadState.Failure) 1.0f else 0.0f
        val spinner = if(state == LoadState.Loading || state == LoadState.Pending) 1.0f else 0.0f
        return LoadViewState(main, failure, spinner)
    }

    fun showFailure(failure: Failure) {
        _pendingRefresh.value = false
        _currentFailure.value = failure
        state.value = LoadState.Failure
    }

    fun showMain() {
        _pendingRefresh.value = false
        _currentFailure.value = null
        state.value = LoadState.Main
    }

    fun showLoading() {
        state.value = LoadState.Loading
    }

    fun showPending() {
        state.value = LoadState.Pending
    }

    fun refreshClicked() {
        loadPending.value?.let { value ->
            if(!value) {
                refresh()
                sendEvent(LoadEvent.ShowRefreshButtonAnimation)
                _pendingRefresh.value = true
            }
        }
    }

    open fun refresh() {}

}