package com.klima7.services.client.features.newjob.jobcreated

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JobCreatedViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowJobsScreen: Event()
    }

    fun start() {
        viewModelScope.launch {
            delay(3000)
            sendEvent(Event.ShowJobsScreen)
        }
    }

}