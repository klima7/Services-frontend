package com.klima7.services.client.features.jobsetup.details

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.platform.CombinedLiveData

class JobDetailsViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowJobCreatedScreen: Event()
    }

    val description = MutableLiveData<String>()
    val realizationTime = MutableLiveData<String>()
    val createButtonEnabled = CombinedLiveData(description, realizationTime) {
        !description.value.isNullOrBlank() && !realizationTime.value.isNullOrBlank()
    }

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