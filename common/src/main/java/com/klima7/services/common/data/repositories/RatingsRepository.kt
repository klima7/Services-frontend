package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.RatingEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating
import kotlinx.coroutines.tasks.await

class RatingsRepository(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions,
) {

    suspend fun getRatingsForExpert(expertId: String, afterId: String?, count: Int):
            Outcome<Failure, List<Rating>> {
        return try {
            var afterDocument: DocumentSnapshot? = null

            if(afterId != null) {
                afterDocument = firestore
                    .collection("ratings")
                    .document(afterId)
                    .get()
                    .await()
            }

            val snapshot = firestore
                .collection("ratings")
                .whereEqualTo("expertId", expertId)
                .orderBy("date", Query.Direction.DESCENDING)
                .orderBy("rating", Query.Direction.DESCENDING)
                .let {
                    if(afterDocument != null)
                        it.startAfter(afterDocument)
                    else
                        it
                }
                .limit(count.toLong())
                .get()
                .await()

            val ratings: List<Rating> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(RatingEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(ratings)
        }
        catch(e: Exception) {
            Log.e("Hello", "Error during getRatingsForExpert", e)
            Outcome.Failure(e.toDomain())
        }
    }

    // addRating

}