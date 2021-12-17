package com.klima7.services.common.data.firebase.dao

import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.JobStatus
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class JobsStatusDao(
    private val functions: FirebaseFunctions
) {

    suspend fun getJobStatus(jobId: String): Outcome<Failure, JobStatus> {
        return try {
            val data = hashMapOf(
                "jobId" to jobId,
            )
            val res = functions
                .getHttpsCallable("jobs-getJobStatus")
                .call(data)
                .await()
            val response = res?.data ?: return Outcome.Failure(Failure.ServerFailure)
            return when(response) {
                0 -> Outcome.Success(JobStatus.NEW)
                1 -> Outcome.Success(JobStatus.REJECTED)
                2 -> Outcome.Success(JobStatus.ACCEPTED)
                else -> Outcome.Failure(Failure.ServerFailure)
            }
        } catch(e: Exception) {
            Timber.e(e, "Error while getJobStatus")
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getNewJobsIds(expertId: String): Outcome<Failure, List<String>> {
        return try {
            val data = hashMapOf(
                "expertId" to expertId
            )
            val res = functions
                .getHttpsCallable("jobs-getNewIds")
                .call(data)
                .await()
            try {
                @Suppress("UNCHECKED_CAST")
                val list = res.data as List<String>
                Outcome.Success(list)
            } catch(e: Exception) {
                Outcome.Failure(Failure.ServerFailure)
            }
        } catch(e: Exception) {
            Timber.e(e, "Error while getAvailableJobIds")
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getRejectedJobsIds(expertId: String): Outcome<Failure, List<String>> {
        return try {
            val data = hashMapOf(
                "expertId" to expertId
            )
            val res = functions
                .getHttpsCallable("jobs-getRejectedIds")
                .call(data)
                .await()
            try {
                @Suppress("UNCHECKED_CAST")
                val list = res.data as List<String>
                Outcome.Success(list)
            } catch(e: Exception) {
                Outcome.Failure(Failure.ServerFailure)
            }
        } catch(e: Exception) {
            Timber.e(e, "Error while getRejectedJobsIds")
            Outcome.Failure(e.toDomain())
        }
    }

}