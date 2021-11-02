package com.klima7.services.expert.features.jobs.base

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.models.ExpertJob
import com.klima7.services.common.models.Failure
import com.klima7.services.expert.features.job.GetCurrentExpertJobUC

class GetCurrentExpertJobsUC(
    private val getCurrentExpertJobUC: GetCurrentExpertJobUC
): BaseUC<GetCurrentExpertJobsUC.Params, List<ExpertJob>>() {

    data class Params(val jobsIds: List<String>)

    override suspend fun execute(params: Params): Outcome<Failure, List<ExpertJob>> {
        val result = mutableListOf<ExpertJob>()

        for(id in params.jobsIds) {
            when(val outcome = getCurrentExpertJobUC.run(GetCurrentExpertJobUC.Params(id))) {
                is Outcome.Success -> result.add(outcome.b)
                is Outcome.Failure -> return Outcome.Failure(outcome.a)
            }
        }

        return Outcome.Success(result)
    }
}