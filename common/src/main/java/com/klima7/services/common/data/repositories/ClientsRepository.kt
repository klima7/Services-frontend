package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.ClientEntity
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.ClientInfo
import com.klima7.services.common.models.Failure
import kotlinx.coroutines.tasks.await

class ClientsRepository(
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions
) {

    suspend fun createClientAccount(): Outcome<Failure, None> {
        return try {
            functions
                .getHttpsCallable("clients-createClientAccount")
                .call()
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while createClientAccount", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getClient(uid: String): Outcome<Failure, Client> {
        try {
            val snapshot = firestore
                .collection("clients")
                .document(uid)
                .get()
                .await()
            val expertEntity = snapshot.toObject(ClientEntity::class.java)
            val expert = expertEntity?.toDomain(uid, snapshot.metadata.isFromCache)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(expert)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getClient", e)
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
            Log.e("Hello", "Error while setClientInfo", e)
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
            Log.e("Hello", "Error while clients-deleteAccount", e)
            Outcome.Failure(e.toDomain())
        }
    }

}