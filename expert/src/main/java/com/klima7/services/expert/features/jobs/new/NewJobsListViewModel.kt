package com.klima7.services.expert.features.jobs.new

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.expert.features.jobs.base.BaseJobsListViewModel
import com.klima7.services.expert.features.jobs.base.GetCurrentExpertJobsUC
import com.klima7.services.expert.usecases.GetCurrentExpertServicesUC
import com.klima7.services.expert.usecases.RejectJobUC

class NewJobsListViewModel(
    getNewJobsIdsUC: GetNewJobsIdsUC,
    getCurrentExpertJobsUC: GetCurrentExpertJobsUC,
    getCurrentExpertServicesUC: GetCurrentExpertServicesUC,
    private val rejectJobUC: RejectJobUC
): BaseJobsListViewModel(getNewJobsIdsUC, getCurrentExpertJobsUC, getCurrentExpertServicesUC) {

    sealed class Event: BaseEvent() {
        class ShowRejectFailure(val failure: Failure): Event()
    }

    private var lastRejectedJobId: String? = null

    fun jobSwiped(jobId: String) {
        rejectJob(jobId)
    }

    fun retryReject() {
        lastRejectedJobId?.let { id ->
            rejectJob(id)
        }
    }

    private fun rejectJob(jobId: String) {
        lastRejectedJobId = jobId
        hideJob(jobId)
        loadState.value = LoadAreaView.State.PENDING
        rejectJobUC.start(
            viewModelScope,
            RejectJobUC.Params(jobId),
            { failure ->
                loadState.value = LoadAreaView.State.MAIN
                showJob(jobId)
                sendEvent(Event.ShowRejectFailure(failure))
            },
            {
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}
