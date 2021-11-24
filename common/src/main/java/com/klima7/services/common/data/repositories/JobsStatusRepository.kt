package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.sources.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.JobStatus

class JobsStatusRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun getJobStatus(expertId: String, jobId: String): Outcome<Failure, JobStatus> {
        return firebase.jobsStatusDao.getJobStatus(expertId, jobId)
    }

    suspend fun getNewJobsIds(expertId: String): Outcome<Failure, List<String>> {
        return firebase.jobsStatusDao.getNewJobsIds(expertId)
    }

    suspend fun getRejectedJobsIds(expertId: String): Outcome<Failure, List<String>> {
        return firebase.jobsStatusDao.getRejectedJobsIds(expertId)
    }

}