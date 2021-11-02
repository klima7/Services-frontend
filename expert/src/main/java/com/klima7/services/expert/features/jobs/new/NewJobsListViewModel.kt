package com.klima7.services.expert.features.jobs.new

import androidx.lifecycle.viewModelScope
import com.klima7.services.expert.features.jobs.base.BaseJobsListViewModel
import com.klima7.services.expert.features.jobs.base.GetCurrentExpertJobsUC
import com.klima7.services.expert.usecases.RejectJobUC

class NewJobsListViewModel(
    getNewJobsIdsUC: GetNewJobsIdsUC,
    getCurrentExpertJobsUC: GetCurrentExpertJobsUC,
    private val rejectJobUC: RejectJobUC
): BaseJobsListViewModel(getNewJobsIdsUC, getCurrentExpertJobsUC) {

    sealed class Event: BaseEvent() {
        object ShowRejectFailure: Event()
    }

    fun jobSwiped(jobId: String) {
        rejectJob(jobId)
    }

    private fun rejectJob(jobId: String) {
        hideJob(jobId);
        showPending()
        rejectJobUC.start(
            viewModelScope,
            RejectJobUC.Params(jobId),
            {
                showMain()
                showJob(jobId)
                sendEvent(Event.ShowRejectFailure);
            },
            {
                showMain()
            }
        )
    }

}
