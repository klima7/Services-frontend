package com.klima7.services.common.data.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.ExpertEntity
import com.klima7.services.common.data.entities.toDomain
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.ExpertInfo
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.util.None
import com.klima7.services.common.domain.util.Outcome
import kotlinx.coroutines.tasks.await

class ExpertsRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions,
    private val storage: FirebaseStorage
) {

    suspend fun createExpertAccount(): Outcome<Failure, None> {
        return try {
            functions
                .getHttpsCallable("experts-createExpertAccount")
                .call()
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while createExpertAccount", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getExpert(uid: String): Outcome<Failure, Expert> {
        try {
            val snapshot = firestore
                .collection("experts")
                .document(uid)
                .get()
                .await()
            val expertEntity = snapshot.toObject(ExpertEntity::class.java)
            val expert = expertEntity?.toDomain(snapshot.metadata.isFromCache)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(expert)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getExpert", e)
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun setExpertInfo(info: ExpertInfo): Outcome<Failure, None> {
        return try {
            val data = hashMapOf(
                "name" to info.name,
                "company" to info.company,
                "description" to info.description,
                "phone" to info.phone,
                "email" to info.email,
                "website" to info.website,
            )

            functions
                .getHttpsCallable("experts-setInfo")
                .call(data)
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while setExpertInfo", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun setProfileImage(imageUri: String): Outcome<Failure, None> {
        return try {
            val uid = auth.currentUser?.uid
            if(uid == null)
                Outcome.Failure(Failure.PermissionFailure)
            storage.reference
                .child("profile_images")
                .child("$uid")
                .putFile(Uri.parse(imageUri))
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while setExpertInfo", e)
            Outcome.Failure(e.toDomain())
        }
    }

    // setLocation
    // setServices
}