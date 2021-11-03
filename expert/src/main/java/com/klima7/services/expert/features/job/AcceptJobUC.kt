package com.klima7.services.expert.features.job

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.JobsRepository

class AcceptJobUC(
    private val jobsRepository: JobsRepository
): BaseUC<AcceptJobUC.Params, None>() {

    data class Params(val jobId: String)

    override suspend fun execute(params: Params) = jobsRepository.acceptJob(params.jobId)
}