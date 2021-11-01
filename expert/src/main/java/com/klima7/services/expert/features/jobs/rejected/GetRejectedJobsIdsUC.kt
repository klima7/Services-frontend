package com.klima7.services.expert.features.jobs.rejected

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.JobsStatusRepository
import com.klima7.services.expert.features.jobs.base.BaseGetJobsIdsUC

class GetRejectedJobsIdsUC(
    authRepository: AuthRepository,
    private val jobsStatusRepository: JobsStatusRepository
): BaseGetJobsIdsUC(authRepository) {

    override suspend fun getIdsPart(uid: String) = jobsStatusRepository.getRejectedJobsIds(uid)
}