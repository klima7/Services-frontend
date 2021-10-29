package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.JobEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import kotlinx.coroutines.tasks.await

class JobsRepository(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions
) {

    suspend fun getJob(id: String): Outcome<Failure, Job> {
        Log.i("Hello", "Getting job $id")
        try {
            val snapshot = firestore
                .collection("jobs")
                .document(id)
                .get()
                .await()
            val jobEntity = snapshot.toObject(JobEntity::class.java)
            val job = jobEntity?.toDomain(id)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(job)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getJob", e)
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getNewJobsIds(): Outcome<Failure, List<String>> {
        return try {
            val res = functions
                .getHttpsCallable("jobs-getNewIds")
                .call()
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

    suspend fun getRejectedJobsIds(): Outcome<Failure, List<String>> {
        return try {
            val res = functions
                .getHttpsCallable("jobs-getRejectedIds")
                .call()
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

    suspend fun acceptJob(jobId: String): Outcome<Failure, None> {
        val data = hashMapOf(
            "id" to jobId
        )
        try {
            functions
                .getHttpsCallable("jobs-accept")
                .call(data)
                .await()
        } catch(e: Exception) {
            Log.e("Hello", "Error while acceptJob", e)
            Outcome.Failure(e.toDomain())
        }
        return Outcome.Success(None())
    }

    suspend fun rejectJob(jobId: String): Outcome<Failure, None> {
        val data = hashMapOf(
            "id" to jobId
        )
        try {
            functions
                .getHttpsCallable("jobs-reject")
                .call(data)
                .await()
        } catch(e: Exception) {
            Log.e("Hello", "Error while rejectJob", e)
            return Outcome.Failure(e.toDomain())
        }
        return Outcome.Success(None())
    }

}
