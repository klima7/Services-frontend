package com.klima7.services.expert.features.job

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.usecases.GetJobUC

class JobViewModel(
    private val getJobUC: GetJobUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object GoBack: Event()
    }

    private lateinit var jobId: String
    val job = MutableLiveData<Job>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()
    val title = job.map { job -> job.serviceName }
    val subtitle = job.map { job -> job.clientName }

    fun start(jobId: String) {
        this.jobId = jobId
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun backClicked() {
        sendEvent(Event.GoBack)
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