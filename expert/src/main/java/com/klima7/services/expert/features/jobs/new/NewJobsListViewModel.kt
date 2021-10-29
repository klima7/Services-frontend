package com.klima7.services.expert.features.jobs.new

import com.klima7.services.expert.features.jobs.base.BaseJobsListViewModel
import com.klima7.services.expert.features.jobs.base.GetJobsUC

class NewJobsListViewModel(
    getNewJobsIdsUC: GetNewJobsIdsUC,
    getJobsUC: GetJobsUC,
): BaseJobsListViewModel(getNewJobsIdsUC, getJobsUC)
