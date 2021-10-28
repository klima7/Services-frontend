package com.klima7.services.expert.usecases

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.JobsRepository

class RejectJobUC(
    private val jobsRepository: JobsRepository
): BaseUC<RejectJobUC.Params, None>() {

    data class Params(val jobId: String)

    override suspend fun execute(params: Params) = jobsRepository.rejectJob(params.jobId)
}