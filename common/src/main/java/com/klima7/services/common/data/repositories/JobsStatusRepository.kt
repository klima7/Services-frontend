package com.klima7.services.common.data.repositories

import android.util.Log
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.data.room.RoomSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.JobStatus

class JobsStatusRepository(
    private val firebase: FirebaseSource,
    room: RoomSource,
) {

    private val roomDao = room.jobStatusDao()

    suspend fun getJobStatus(expertId: String, jobId: String): Outcome<Failure, JobStatus> {
        val firebaseRes = firebase.jobsStatusDao.getJobStatus(jobId)
        if(firebaseRes is Outcome.Success) {
            val status = firebaseRes.b
            roomDao.updateSingle(expertId, jobId, status)
            return firebaseRes
        }
        return roomDao.getJobStatus(expertId, jobId)
    }

    suspend fun getNewJobsIds(expertId: String): Outcome<Failure, List<String>> {
        val firebaseRes = firebase.jobsStatusDao.getNewJobsIds(expertId)
        if(firebaseRes is Outcome.Success) {
            val jobIds = firebaseRes.b
            roomDao.updateMultiple(expertId, jobIds, JobStatus.NEW)
            return firebaseRes
        }
        return roomDao.getNewJobsIds(expertId)
    }

    suspend fun getRejectedJobsIds(expertId: String): Outcome<Failure, List<String>> {
        val firebaseRes = firebase.jobsStatusDao.getRejectedJobsIds(expertId)
        if(firebaseRes is Outcome.Success) {
            val jobIds = firebaseRes.b
            roomDao.updateMultiple(expertId, jobIds, JobStatus.REJECTED)
            return firebaseRes
        }
        return roomDao.getRejectedJobsIds(expertId)
    }

}