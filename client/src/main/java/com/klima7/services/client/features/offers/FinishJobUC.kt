package com.klima7.services.client.features.offers

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.JobsRepository

class FinishJobUC(
    private val jobsRepository: JobsRepository
): BaseUC<FinishJobUC.Params, None>() {

    data class Params(val jobId: String)

    override suspend fun execute(params: Params) = jobsRepository.finishJob(params.jobId)
}