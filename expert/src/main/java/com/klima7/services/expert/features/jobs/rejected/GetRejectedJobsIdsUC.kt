package com.klima7.services.expert.features.jobs.rejected

import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.JobsRepository
import com.klima7.services.expert.features.jobs.base.BaseGetJobsIdsUC

class GetRejectedJobsIdsUC(
    private val jobsRepository: JobsRepository
): BaseGetJobsIdsUC() {

    override suspend fun execute(params: None) = jobsRepository.getRejectedJobsIds()
}