package com.klima7.services.client.usecases

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.data.repositories.JobsRepository
import com.klima7.services.common.models.Job

class GetJobUC(
    private val jobsRepository: JobsRepository
): BaseUC<GetJobUC.Params, Job>() {

    data class Params(val jobId: String)

    override suspend fun execute(params: Params) = jobsRepository.getJob(params.jobId)
}