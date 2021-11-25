package com.klima7.services.client.features.jobsetup.details

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.JobsRepository

class CreateJobUC(
    private val jobsRepository: JobsRepository
): BaseUC<CreateJobUC.Params, None>() {

    data class Params(val serviceId: String, val placeId: String, val description: String,
                      val realizationTime: String)

    override suspend fun execute(params: Params) =
        jobsRepository.createJob(params.serviceId, params.placeId,
            params.description, params.realizationTime)

}