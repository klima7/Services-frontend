package com.klima7.services.expert.features.job

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.JobsRepository
import com.klima7.services.common.data.repositories.JobsStatusRepository
import com.klima7.services.common.models.ExpertJob
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import com.klima7.services.common.models.JobStatus

class GetCurrentExpertJobUC(
    private val jobsRepository: JobsRepository,
    private val jobsStatusRepository: JobsStatusRepository,
    private val authRepository: AuthRepository
): BaseUC<GetCurrentExpertJobUC.Params, ExpertJob>() {

    data class Params(val jobId: String)

    override suspend fun execute(params: Params): Outcome<Failure, ExpertJob> {
        return getUidPart(params.jobId)
    }

    private suspend fun getUidPart(jobId: String): Outcome<Failure, ExpertJob> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null) {
                return@foldS Outcome.Failure(Failure.PermissionFailure)
            }
            getJobPart(jobId, uid)
        })
    }

    private suspend fun getJobPart(jobId: String, uid: String): Outcome<Failure, ExpertJob> {
        return jobsRepository.getJob(jobId).foldS({ failure ->
            Outcome.Failure(failure)
        }, { job ->
            getStatusPart(jobId, uid, job)
        })
    }

    private suspend fun getStatusPart(jobId: String, uid: String, job: Job): Outcome<Failure, ExpertJob> {
        return jobsStatusRepository.getJobStatus(uid, jobId).foldS({ failure ->
            Outcome.Failure(failure)
        }, { status ->
            combineParts(job, status)
        })
    }

    private fun combineParts(job: Job, status: JobStatus): Outcome<Failure, ExpertJob> {
        val result = ExpertJob(job, status)
        return Outcome.Success(result)
    }
}