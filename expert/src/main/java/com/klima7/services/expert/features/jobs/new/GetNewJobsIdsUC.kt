package com.klima7.services.expert.features.jobs.new

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.JobsStatusRepository
import com.klima7.services.expert.features.jobs.base.BaseGetJobsIdsUC

class GetNewJobsIdsUC(
    authRepository: AuthRepository,
    private val jobsStatusRepository: JobsStatusRepository
): BaseGetJobsIdsUC(authRepository) {

    override suspend fun getIdsPart(uid: String) = jobsStatusRepository.getNewJobsIds(uid)
}