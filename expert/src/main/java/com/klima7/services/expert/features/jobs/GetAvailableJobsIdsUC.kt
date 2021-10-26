package com.klima7.services.expert.features.jobs

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.JobsRepository

class GetAvailableJobsIdsUC(
    private val jobsRepository: JobsRepository
): BaseUC<None, List<String>>() {

    override suspend fun execute(params: None) = jobsRepository.getAvailableJobIds()
}