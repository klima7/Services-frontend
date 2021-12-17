package com.klima7.services.common.data.firebase.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.converters.toDomain
import com.klima7.services.common.data.firebase.entities.ClientEntity
import com.klima7.services.common.data.firebase.utils.getEnhanced
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.ClientInfo
import com.klima7.services.common.models.Failure
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ClientsDao(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions
) {

    suspend fun createClientAccount(): Outcome<Failure, None> {
        return try {
            functions
                .getHttpsCallable("clients-createAccount")
                .call()
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error while createClientAccount")
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getClient(uid: String): Outcome<Failure, Client> {
        try {
            val snapshot = firestore
                .collection("clients")
                .document(uid)
                .getEnhanced()
                .await()
            val clientEntity = snapshot.toObject(ClientEntity::class.java)
            val client = clientEntity?.toDomain(uid, snapshot.metadata.isFromCache)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(client)
        } catch(e: Exception) {
            Timber.e(e, "Error during getClient")
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun setClientInfo(info: ClientInfo): Outcome<Failure, None> {
        return try {
            val data = hashMapOf(
                "name" to info.name,
                "phone" to info.phone,
            )

            functions
                .getHttpsCallable("clients-setInfo")
                .call(data)
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error while setClientInfo")
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun deleteAccount(): Outcome<Failure, None> {
        return try {
            functions
                .getHttpsCallable("clients-deleteAccount")
                .call()
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error while clients-deleteAccount")
            Outcome.Failure(e.toDomain())
        }
    }

}