package com.klima7.services.expert.features.jobs.rejected

import com.klima7.services.expert.features.jobs.base.BaseJobsListViewModel
import com.klima7.services.expert.features.jobs.base.GetCurrentExpertJobsUC

class RejectedJobsListViewModel(
    getNewJobsIdsUC: GetRejectedJobsIdsUC,
    getCurrentExpertJobsUC: GetCurrentExpertJobsUC,
): BaseJobsListViewModel(getNewJobsIdsUC, getCurrentExpertJobsUC)
