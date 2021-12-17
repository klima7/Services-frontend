package com.klima7.services.common.data.firebase.dao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class TokensStorageDao(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) {

    suspend fun updateToken(role: Role, token: String): Outcome<Failure, None> {
        try {
            val uid = auth.currentUser?.uid ?: return Outcome.Failure(Failure.PermissionFailure)
            val collectionName = if(role == Role.CLIENT) "clients" else "experts"
            val tokensCollection = firestore
                .collection(collectionName)
                .document(uid)
                .collection("tokens")

            val documents = tokensCollection
                .whereEqualTo("token", token)
                .get(Source.SERVER)
                .await().documents

            if(documents.isEmpty()) {
                val data = hashMapOf(
                    "token" to token,
                    "time" to FieldValue.serverTimestamp(),
                )
                tokensCollection
                    .add(data)
                    .await()
                return Outcome.Success(None())
            }

            else {
                val id = documents[0].id
                val data: Map<String, Any> = hashMapOf("time" to FieldValue.serverTimestamp())
                tokensCollection
                    .document(id)
                    .update(data)
                    .await()
                return Outcome.Success(None())
            }

        } catch(e: Exception) {
            Timber.e(e, "Error during updateToken")
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun deleteToken(role: Role, token: String): Outcome<Failure, None> {
        val uid = auth.currentUser?.uid ?: return Outcome.Failure(Failure.PermissionFailure)
        val collectionName = if(role == Role.CLIENT) "clients" else "experts"
        val tokensCollection = firestore
            .collection(collectionName)
            .document(uid)
            .collection("tokens")
        return try {
            val documents = tokensCollection
                .whereEqualTo("token", token)
                .get()
                .await().documents

            documents.forEach { document ->
                document.reference.delete()
            }
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error during updateToken")
            Outcome.Failure(e.toDomain())
        }
    }
}
