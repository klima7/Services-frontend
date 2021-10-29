package com.klima7.services.expert.features.jobs.rejected

import com.klima7.services.expert.features.jobs.base.BaseJobsListViewModel
import com.klima7.services.expert.features.jobs.base.GetJobsUC

class RejectedJobsListViewModel(
    getNewJobsIdsUC: GetRejectedJobsIdsUC,
    getJobsUC: GetJobsUC,
): BaseJobsListViewModel(getNewJobsIdsUC, getJobsUC)
