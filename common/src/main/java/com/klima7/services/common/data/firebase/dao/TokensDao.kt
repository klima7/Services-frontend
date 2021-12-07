package com.klima7.services.common.data.firebase.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.messaging.FirebaseMessaging
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.converters.toDomain
import com.klima7.services.common.data.firebase.entities.MessageEntity
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.Role
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TokensDao(
    private val messaging: FirebaseMessaging
) {

    suspend fun getToken(): Outcome<Failure, String> {
        return try {
            val token = messaging.token.await()
            Outcome.Success(token)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getToken", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun deleteToken(): Outcome<Failure, None> {
        return try {
            messaging.deleteToken().await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error during deleteToken", e)
            Outcome.Failure(e.toDomain())
        }
    }

}
