package com.klima7.services.common.data.room.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.room.entities.JobStatusEntity
import com.klima7.services.common.data.room.entities.LastLocationEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.JobStatus
import java.lang.Exception

@Dao
interface JobStatusDao {

    @Query("SELECT * FROM JobStatusEntity WHERE uid=:uid AND jobId=:jobId")
    fun roomGetByJob(uid: String, jobId: String): JobStatusEntity?

    @Query("SELECT * FROM JobStatusEntity WHERE uid=:uid AND status=:status")
    fun roomGetByStatus(uid: String, status: Int): List<JobStatusEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun roomInsertOrUpdate(statusEntity: JobStatusEntity)

    @Query("DELETE FROM JobStatusEntity WHERE uid = :uid AND status=:status AND jobId NOT IN (:presentJobIds)")
    fun roomDeleteOther(uid: String, status: Int, presentJobIds: List<String>)

    fun updateSingle(uid: String, jobId: String, status: JobStatus) {
        try {
            val entity = JobStatusEntity(uid, jobId, JobStatusEntity.jobStatusToInt(status))
            roomInsertOrUpdate(entity)
        } catch(e: Exception) {
            Log.i("Hello", "room-updateSingle EXCEPTION", e)
        }
    }

    fun updateMultiple(uid: String, jobIds: List<String>, status: JobStatus) {
        jobIds.forEach { jobId -> updateSingle(uid, jobId, status) }
        roomDeleteOther(uid, JobStatusEntity.jobStatusToInt(status), jobIds)
    }

    fun getJobStatus(uid: String, jobId: String): Outcome<Failure, JobStatus> {
        try {
            val entity = roomGetByJob(uid, jobId) ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(JobStatusEntity.intToJobStatus(entity.status))
        } catch(e: Exception) {
            return Outcome.Failure(Failure.UnknownFailure)
        }
    }

    fun getRejectedJobsIds(uid: String) = getJobsIds(uid, JobStatus.REJECTED)

    fun getNewJobsIds(uid: String) = getJobsIds(uid, JobStatus.NEW)

    private fun getJobsIds(uid: String, status: JobStatus): Outcome<Failure, List<String>> {
        return try {
            val entities = roomGetByStatus(uid, JobStatusEntity.jobStatusToInt(status))
            val ids = entities.map { it.jobId }
            Outcome.Success(ids)
        } catch(e: Exception) {
            Outcome.Failure(Failure.UnknownFailure)
        }
    }

}
