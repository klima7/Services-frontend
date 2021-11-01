package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.JobStatus
import kotlinx.coroutines.tasks.await

class JobsStatusRepository(
    private val functions: FirebaseFunctions
) {

    suspend fun getJobStatus(expertId: String, jobId: String): Outcome<Failure, JobStatus> {
        return Outcome.Failure(Failure.UnknownFailure)
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
            Log.e("Hello", "Error while getAvailableJobIds", e)
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
            Log.e("Hello", "Error while getRejectedJobsIds", e)
            Outcome.Failure(e.toDomain())
        }
    }

}