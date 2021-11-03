package com.klima7.services.expert.features.job

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.ExpertJob
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.JobStatus
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.usecases.RejectJobUC

class JobViewModel(
    private val getCurrentExpertJobUC: GetCurrentExpertJobUC,
    private val rejectJobUC: RejectJobUC,
    private val acceptJobUC: AcceptJobUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object FinishWithAccept: Event()
        object FinishWithReject: Event()
        object FinishWithNoop: Event()
    }

    lateinit var jobId: String
    val expertJob = MutableLiveData<ExpertJob>()
    val rejectButtonVisible = expertJob.map { it.status == JobStatus.NEW }

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
        sendEvent(Event.FinishWithNoop)
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

    fun acceptClicked() {
        acceptJob(jobId)
    }

    fun rejectClicked() {
        rejectJob(jobId)
    }

    private fun rejectJob(jobId: String) {
        loadState.value = LoadAreaView.State.PENDING
        rejectJobUC.start(
            viewModelScope,
            RejectJobUC.Params(jobId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            {
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.FinishWithReject)
            }
        )
    }

    private fun acceptJob(jobId: String) {
        loadState.value = LoadAreaView.State.PENDING
        acceptJobUC.start(
            viewModelScope,
            AcceptJobUC.Params(jobId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            {
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.FinishWithReject)
            }
        )
    }

}