package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.ProfileImageEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.ProfileImage
import com.klima7.services.common.core.Outcome
import kotlinx.coroutines.tasks.await

class ProfileImagesRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun getProfileImage(expertUid: String): Outcome<Failure, ProfileImage?> {
        try {
            val snapshot = firestore
                .collection("profile_images")
                .document(expertUid)
                .get()
                .await()
            val profileImageEntity = snapshot.toObject(ProfileImageEntity::class.java)
            val profileImage = profileImageEntity?.toDomain()
                ?: return Outcome.Success(null)
            return Outcome.Success(profileImage)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getProfileImage", e)
            return Outcome.Failure(e.toDomain())
        }
    }

}