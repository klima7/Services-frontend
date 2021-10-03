package com.klima7.services.common.repo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.klima7.services.common.entities.ExpertEntity
import com.klima7.services.common.entities.toDomain
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.util.Outcome
import com.klima7.services.common.util.toDomain
import kotlinx.coroutines.tasks.await

class ExpertsRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun getExpert(uid: String): Outcome<Failure, Expert> {
        try {
            val snapshot = firestore
                .collection("experts")
                .document(uid)
                .get()
                .await()
            val expertEntity = snapshot.toObject(ExpertEntity::class.java)
            val expert = expertEntity?.toDomain(snapshot.metadata.isFromCache)
                ?: return Outcome.Failure(Failure.ExpertNotFoundFailure)
            return Outcome.Success(expert)
        } catch(e: FirebaseFirestoreException) {
            return Outcome.Failure(e.toDomain())
        }
    }

    // setProfileImage
    // setPrimaryInfo
    // setLocation
    // setServices
}