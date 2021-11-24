package com.klima7.services.common.data.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.sources.firebase.converters.toDomain
import com.klima7.services.common.data.sources.firebase.entities.ExpertEntity
import com.klima7.services.common.data.sources.firebase.utils.toDomain
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.ExpertInfo
import com.klima7.services.common.models.Failure
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
            val expert = expertEntity?.toDomain(uid, snapshot.metadata.isFromCache)
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

    suspend fun setProfileImage(profileImageUrl: String): Outcome<Failure, None> {
        return try {
            val uid = auth.currentUser?.uid
            if(uid == null)
                Outcome.Failure(Failure.PermissionFailure)
            storage.reference
                .child("profile_images")
                .child("$uid.png")
                .putFile(Uri.parse(profileImageUrl))
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while setExpertImage", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun clearProfileImage(): Outcome<Failure, None> {
        return try {
            val uid = auth.currentUser?.uid
            if(uid == null)
                Outcome.Failure(Failure.PermissionFailure)
            storage.reference
                .child("profile_images")
                .child("$uid.png")
                .delete()
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while clearProfileImage", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun setServicesIds(services: List<String>): Outcome<Failure, None> {
        return try {
            val data = hashMapOf(
                "services" to services,
            )

            functions
                .getHttpsCallable("experts-setServices")
                .call(data)
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while setServicesIds", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun setWorkingArea(placeId: String, radius: Int): Outcome<Failure, None> {
        return try {
            val data = hashMapOf(
                "placeId" to placeId,
                "radius" to radius,
            )

            functions
                .getHttpsCallable("experts-setWorkingArea")
                .call(data)
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while setWorkingArea", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun deleteAccount(): Outcome<Failure, None> {
        return try {
            functions
                .getHttpsCallable("experts-deleteAccount")
                .call()
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while experts-deleteAccount", e)
            Outcome.Failure(e.toDomain())
        }
    }
}