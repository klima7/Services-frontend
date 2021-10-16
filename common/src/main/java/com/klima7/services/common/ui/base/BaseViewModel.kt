package com.klima7.services.common.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    val TAG: String = this::class.java.simpleName

    abstract class BaseEvent

    private val eventChannel = Channel<BaseEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun sendEvent(event: BaseEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}