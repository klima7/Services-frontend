package com.klima7.services.client.features.jobs

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job

class GetCurrentClientJobsUC: BaseUC<GetCurrentClientJobsUC.Params, List<Job>>() {

    data class Params(val afterId: String?, val count: Int)

    override suspend fun execute(params: Params): Outcome<Failure, List<Job>> {
        return Outcome.Failure(Failure.UnknownFailure)
    }
}