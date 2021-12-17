package com.klima7.services.common.data.firebase.dao

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.converters.toDomain
import com.klima7.services.common.data.firebase.entities.ExpertEntity
import com.klima7.services.common.data.firebase.utils.getEnhanced
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.ExpertInfo
import com.klima7.services.common.models.Failure
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ExpertsDao(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions,
    private val storage: FirebaseStorage
) {

    suspend fun createExpertAccount(): Outcome<Failure, None> {
        return try {
            functions
                .getHttpsCallable("experts-createAccount")
                .call()
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error while createExpertAccount")
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getExpert(uid: String): Outcome<Failure, Expert> {
        try {
            val snapshot = firestore
                .collection("experts")
                .document(uid)
                .getEnhanced()
                .await()
            val expertEntity = snapshot.toObject(ExpertEntity::class.java)
            val expert = expertEntity?.toDomain(uid, snapshot.metadata.isFromCache)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(expert)
        } catch(e: Exception) {
            Timber.e(e, "Error during getExpert")
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
            Timber.e(e, "Error while setExpertInfo")
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
            Timber.e(e, "Error while setExpertImage")
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
            Timber.e(e, "Error while clearProfileImage")
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
            Timber.e(e, "Error while setServicesIds")
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
            Timber.e(e, "Error while setWorkingArea")
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
            Timber.e(e, "Error while experts-deleteAccount")
            Outcome.Failure(e.toDomain())
        }
    }
}