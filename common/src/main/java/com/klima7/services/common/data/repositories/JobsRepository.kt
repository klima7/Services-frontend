package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.sources.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job

class JobsRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun getJob(id: String): Outcome<Failure, Job> {
        return firebase.jobsDao.getJob(id)
    }

    suspend fun acceptJob(jobId: String): Outcome<Failure, None> {
        return firebase.jobsDao.acceptJob(jobId)
    }

    suspend fun rejectJob(jobId: String): Outcome<Failure, None> {
        return firebase.jobsDao.rejectJob(jobId)
    }

    suspend fun getClientJobs(clientId: String, afterId: String?, count: Int): Outcome<Failure, List<Job>> {
        return firebase.jobsDao.getClientJobs(clientId, afterId, count)
    }

    suspend fun finishJob(jobId: String): Outcome<Failure, None> {
        return firebase.jobsDao.finishJob(jobId)
    }

}
