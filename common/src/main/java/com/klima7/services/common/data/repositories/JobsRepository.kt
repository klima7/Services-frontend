package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.JobEntity
import com.klima7.services.common.data.entities.OfferEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import com.klima7.services.common.models.Offer
import kotlinx.coroutines.tasks.await

class JobsRepository(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions
) {

    suspend fun getJob(id: String): Outcome<Failure, Job> {
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

    suspend fun getClientJobs(clientId: String, afterId: String?, count: Int): Outcome<Failure, List<Job>> {
        return try {
            var afterDocument: DocumentSnapshot? = null

            if(afterId != null) {
                afterDocument = firestore
                    .collection("jobs")
                    .document(afterId)
                    .get()
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
                .limit(count.toLong())
                .get()
                .await()

            val jobs: List<Job> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(JobEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(jobs)
        }
        catch(e: Exception) {
            Log.e("Hello", "Error during getClientJobs", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun finishJob(jobId: String): Outcome<Failure, None> {
        val data = hashMapOf(
            "id" to jobId
        )
        try {
            functions
                .getHttpsCallable("jobs-finish")
                .call(data)
                .await()
        } catch(e: Exception) {
            Log.e("Hello", "Error while finishJob", e)
            return Outcome.Failure(e.toDomain())
        }
        return Outcome.Success(None())
    }

}
