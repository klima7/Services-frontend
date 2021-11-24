package com.klima7.services.common.data.firebase.dao

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.converters.toDomain
import com.klima7.services.common.data.firebase.entities.OfferEntity
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class OffersDao(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions,
) {

    suspend fun getOffersForExpert(expertId: String, archived: Boolean, afterId: String?, count: Int):
            Outcome<Failure, List<Offer>> {
        return try {
            var afterDocument: DocumentSnapshot? = null

            if(afterId != null) {
                afterDocument = firestore
                    .collection("offers")
                    .document(afterId)
                    .get()
                    .await()
            }

            val snapshot = firestore
                .collection("offers")
                .whereEqualTo("expertId", expertId)
                .whereEqualTo("archived", archived)
                .orderBy("creationTime", Query.Direction.DESCENDING)
                .let {
                    if(afterDocument != null)
                        it.startAfter(afterDocument)
                    else
                        it
                }
                .limit(count.toLong())
                .get()
                .await()

            val offers: List<Offer> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(OfferEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(offers)
        }
        catch(e: Exception) {
            Log.e("Hello", "Error during getOffersForExpert", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getOffersForJob(jobId: String): Outcome<Failure, List<Offer>> {
        return try {
            val snapshot = firestore
                .collection("offers")
                .whereEqualTo("jobId", jobId)
                .get()
                .await()

            val offers: List<Offer> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(OfferEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(offers)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getOffersForJob", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getOffer(id: String): Outcome<Failure, Offer> {
        try {
            val snapshot = firestore
                .collection("offers")
                .document(id)
                .get()
                .await()
            val offerEntity = snapshot.toObject(OfferEntity::class.java)
            val offer = offerEntity?.toDomain(id)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(offer)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getOffer", e)
            return Outcome.Failure(e.toDomain())
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun getOfferStream(offerId: String): Flow<Offer> = callbackFlow {
        val query = firestore
            .collection("offers")
            .document(offerId)

        val subscription = query.addSnapshotListener(MetadataChanges.INCLUDE) { docSnapshot, _ ->
            if(docSnapshot != null) {
                val entity = docSnapshot.toObject(OfferEntity::class.java)
                val offer = entity?.toDomain(docSnapshot.id)
                if(offer != null) {
                    trySend(offer)
                }
            }
        }

        awaitClose {
            subscription.remove()
        }
    }

    suspend fun setOfferArchived(offerId: String, archived: Boolean): Outcome<Failure, None> {
        val data = hashMapOf(
            "offerId" to offerId,
            "archived" to archived
        )
        return try {
            functions
                .getHttpsCallable("offers-setOfferArchived")
                .call(data)
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while setOfferArchived", e)
            Outcome.Failure(e.toDomain())
        }
    }

}
