package com.klima7.services.client.features.job

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.client.usecases.GetJobUC
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseViewModel

class JobViewModel(
    private val getJobUC: GetJobUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object Finish: Event()
    }

    lateinit var jobId: String
    val job = MutableLiveData<Job>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    val subtitle = job.map { job -> job.serviceName }

    fun start(jobId: String) {
        this.jobId = jobId
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun backClicked() {
        sendEvent(Event.Finish)
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getJobUC.start(
            viewModelScope,
            GetJobUC.Params(jobId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { job ->
                this.job.value = job
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}