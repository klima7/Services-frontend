package com.klima7.services.expert.features.jobs.rejected

import com.klima7.services.expert.features.jobs.base.BaseJobsListViewModel
import com.klima7.services.expert.features.jobs.base.GetCurrentExpertJobsUC
import com.klima7.services.expert.features.jobs.base.GetCurrentExpertServicesUC

class RejectedJobsListViewModel(
    getNewJobsIdsUC: GetRejectedJobsIdsUC,
    getCurrentExpertJobsUC: GetCurrentExpertJobsUC,
    getCurrentExpertServicesUC: GetCurrentExpertServicesUC,
): BaseJobsListViewModel(getNewJobsIdsUC, getCurrentExpertJobsUC, getCurrentExpertServicesUC)
