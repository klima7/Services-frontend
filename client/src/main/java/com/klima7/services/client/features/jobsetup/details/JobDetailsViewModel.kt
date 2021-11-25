package com.klima7.services.client.features.jobsetup.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.SimpleLocation
import com.klima7.services.common.models.SimpleService
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.platform.CombinedLiveData

class JobDetailsViewModel(
    private val createJobUC: CreateJobUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowJobCreatedScreen: Event()
        data class ShowJobCreateFailure(val failure: Failure): Event()
    }

    private lateinit var service: SimpleService
    private lateinit var location: SimpleLocation

    val description = MutableLiveData<String>()
    val realizationTime = MutableLiveData<String>()
    val createButtonEnabled = CombinedLiveData(description, realizationTime) {
        !description.value.isNullOrBlank() && !realizationTime.value.isNullOrBlank()
    }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    fun start(service: SimpleService, location: SimpleLocation) {
        this.service = service
        this.location = location
    }

    fun refresh() {

    }

    fun createJobClicked() {
        val cDescription = description.value
        val cRealizationTime = realizationTime.value
        if(cDescription != null && cRealizationTime != null) {
            createJob(cDescription, cRealizationTime)
        }
    }

    private fun createJob(description: String,
                          realizationTime: String) {
        loadState.value = LoadAreaView.State.PENDING
        createJobUC.start(
            viewModelScope,
            CreateJobUC.Params(service.id, location.placeId, description, realizationTime),
            { failure ->
                Log.i("Hello", "Create job failure $failure")
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowJobCreateFailure(failure))
            },
            {
                Log.i("Hello", "Create job success")
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowJobCreatedScreen)
            }
        )
    }

}