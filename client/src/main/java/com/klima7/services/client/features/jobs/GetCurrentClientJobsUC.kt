package com.klima7.services.client.features.jobs

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.JobsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job

class GetCurrentClientJobsUC(
    private val authRepository: AuthRepository,
    private val jobsRepository: JobsRepository,
): BaseUC<GetCurrentClientJobsUC.Params, List<Job>>() {

    data class Params(val afterId: String?, val count: Int)

    override suspend fun execute(params: Params): Outcome<Failure, List<Job>> {
        return getUidPart(params.afterId, params.count)
    }

    private suspend fun getUidPart(afterId: String?, count: Int): Outcome<Failure, List<Job>> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null) {
                return@foldS Outcome.Failure(Failure.PermissionFailure)
            }
            getJobsPart(uid, afterId, count)
        })
    }

    private suspend fun getJobsPart(uid: String, afterId: String?, count: Int) =
        jobsRepository.getClientJobs(uid, afterId, count)
}