package com.klima7.services.expert.features.job

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.ExpertJob
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel

class JobViewModel(
    private val getCurrentExpertJobUC: GetCurrentExpertJobUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object GoBack: Event()
    }

    private lateinit var jobId: String
    val expertJob = MutableLiveData<ExpertJob>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()
    val title = expertJob.map { expertJob -> expertJob.job.serviceName }
    val subtitle = expertJob.map { expertJob -> expertJob.job.clientName }

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
        getCurrentExpertJobUC.start(
            viewModelScope,
            GetCurrentExpertJobUC.Params(jobId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { expertJob ->
                this.expertJob.value = expertJob
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}