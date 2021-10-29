package com.klima7.services.expert.features.jobs.base

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.JobsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job

class GetJobsUC(
    private val jobsRepository: JobsRepository
): BaseUC<GetJobsUC.Params, List<Job>>() {

    data class Params(val jobsIds: List<String>)

    override suspend fun execute(params: Params): Outcome<Failure, List<Job>> {
        val result = mutableListOf<Job>()

        for(id in params.jobsIds) {
            when(val outcome = jobsRepository.getJob(id)) {
                is Outcome.Success -> result.add(outcome.b)
                is Outcome.Failure -> return Outcome.Failure(outcome.a)
            }
        }

        return Outcome.Success(result)
    }
}