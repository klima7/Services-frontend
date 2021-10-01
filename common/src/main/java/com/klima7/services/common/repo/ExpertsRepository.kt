package com.klima7.services.common.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.klima7.services.common.entities.ExpertEntity
import com.klima7.services.common.entities.toDomain
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.util.Outcome
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
            val expert = expertEntity?.toDomain() ?: return Outcome.Failure(Failure.ExpertNotFoundFailure)
            return Outcome.Success(expert)
        } catch(e: FirebaseFirestoreException) {
            return Outcome.Failure(Failure.ServerFailure)
        }
    }

    // setProfileImage
    // setPrimaryInfo
    // setLocation
    // setServices
}