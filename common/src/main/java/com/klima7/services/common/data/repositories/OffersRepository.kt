package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.OfferEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import kotlinx.coroutines.tasks.await

class OffersRepository(
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

    suspend fun setOfferArchived(offerId: String, archived: Boolean): Outcome<Failure, None> {
        val data = hashMapOf(
            "offerId" to offerId,
            "archived" to archived
        )
        try {
            functions
                .getHttpsCallable("offers-setOfferArchived")
                .call(data)
                .await()
        } catch(e: Exception) {
            Log.e("Hello", "Error while acceptJob", e)
            Outcome.Failure(e.toDomain())
        }
        return Outcome.Success(None())
    }
}
