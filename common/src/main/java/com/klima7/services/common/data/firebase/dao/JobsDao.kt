package com.klima7.services.common.data.firebase.dao

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.converters.toDomain
import com.klima7.services.common.data.firebase.entities.JobEntity
import com.klima7.services.common.data.firebase.utils.getEnhanced
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class JobsDao(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions
) {

    suspend fun getJob(id: String): Outcome<Failure, Job> {
        try {
            val snapshot = firestore
                .collection("jobs")
                .document(id)
                .getEnhanced()
                .await()
            val jobEntity = snapshot.toObject(JobEntity::class.java)
            val job = jobEntity?.toDomain(id)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(job)
        } catch(e: Exception) {
            Timber.e(e, "Error during getJob")
            return Outcome.Failure(e.toDomain())
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
            Timber.e(e, "Error while acceptJob")
            return Outcome.Failure(e.toDomain())
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
            Timber.e(e, "Error while rejectJob")
            return Outcome.Failure(e.toDomain())
        }
        return Outcome.Success(None())
    }

    suspend fun getClientJobs(clientId: String, afterId: String?, count: Int): Outcome<Failure, List<Job>> {
        return try {
            var afterDocument: DocumentSnapshot? = null

            if(afterId != null) {
                afterDocument = firestore
                    .collection("jobs")
                    .document(afterId)
                    .getEnhanced()
                    .await()
            }

            val snapshot = firestore
                .collection("jobs")
                .whereEqualTo("clientId", clientId)
                .let {
                    if(afterDocument != null)
                        it.startAfter(afterDocument)
                    else
                        it
                }
                .orderBy("creation", Query.Direction.DESCENDING)
                .limit(count.toLong())
                .getEnhanced()
                .await()

            val jobs: List<Job> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(JobEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(jobs)
        }
        catch(e: Exception) {
            Timber.e(e, "Error during getClientJobs")
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun finishJob(jobId: String): Outcome<Failure, None> {
        val data = hashMapOf(
            "jobId" to jobId
        )
        try {
            functions
                .getHttpsCallable("jobs-finish")
                .call(data)
                .await()
        } catch(e: Exception) {
            Timber.e(e, "Error while finishJob")
            return Outcome.Failure(e.toDomain())
        }
        return Outcome.Success(None())
    }

    suspend fun createJob(serviceId: String, placeId: String, description: String,
                          realizationTime: String): Outcome<Failure, None> {
        val data = hashMapOf(
            "serviceId" to serviceId,
            "placeId" to placeId,
            "description" to description,
            "realizationTime" to realizationTime,
        )
        try {
            functions
                .getHttpsCallable("jobs-create")
                .call(data)
                .await()
        } catch(e: Exception) {
            Timber.e(e, "Error while createJob")
            return Outcome.Failure(e.toDomain())
        }
        return Outcome.Success(None())
    }

}
