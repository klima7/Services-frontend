package com.klima7.services.common.data.firebase.dao

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.converters.toDomain
import com.klima7.services.common.data.firebase.entities.RatingEntity
import com.klima7.services.common.data.firebase.utils.getEnhanced
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class RatingsDao(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions,
) {

    suspend fun getRating(id: String): Outcome<Failure, Rating> {
        try {
            val snapshot = firestore
                .collection("ratings")
                .document(id)
                .getEnhanced()
                .await()
            val ratingEntity = snapshot.toObject(RatingEntity::class.java)
            val rating = ratingEntity?.toDomain(id)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(rating)
        } catch(e: Exception) {
            Timber.e(e, "Error during getRating")
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getRatingsForExpert(expertId: String, afterId: String?, count: Int):
            Outcome<Failure, List<Rating>> {
        return try {
            var afterDocument: DocumentSnapshot? = null

            if(afterId != null) {
                afterDocument = firestore
                    .collection("ratings")
                    .document(afterId)
                    .getEnhanced()
                    .await()
            }

            val snapshot = firestore
                .collection("ratings")
                .whereEqualTo("expertId", expertId)
                .orderBy("date", Query.Direction.DESCENDING)
                .let {
                    if(afterDocument != null)
                        it.startAfter(afterDocument)
                    else
                        it
                }
                .limit(count.toLong())
                .getEnhanced()
                .await()

            val ratings: List<Rating> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(RatingEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(ratings)
        }
        catch(e: Exception) {
            Timber.e(e, "Error during getRatingsForExpert")
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun addRating(offerId: String, rating: Double, comment: String?): Outcome<Failure, None> {
        return try {
            val data = hashMapOf(
                "offerId" to offerId,
                "rating" to rating,
                "comment" to comment
            )

            functions
                .getHttpsCallable("ratings-add")
                .call(data)
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error while addRating")
            Outcome.Failure(e.toDomain())
        }
    }

}