package com.klima7.services.client.features.newjob.jobdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel

class JobDetailsViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowJobCreatedScreen: Event()
    }

    val description = MutableLiveData<String>()
    val realizationTime = MutableLiveData<String>()

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    fun start() {

    }

    fun refresh() {

    }

    fun createJobClicked() {
        sendEvent(Event.ShowJobCreatedScreen)
    }

}