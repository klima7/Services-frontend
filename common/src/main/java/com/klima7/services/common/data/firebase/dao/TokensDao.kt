package com.klima7.services.common.data.firebase.dao

import com.google.firebase.messaging.FirebaseMessaging
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class TokensDao(
    private val messaging: FirebaseMessaging
) {

    suspend fun getToken(): Outcome<Failure, String> {
        return try {
            val token = messaging.token.await()
            Outcome.Success(token)
        } catch(e: Exception) {
            Timber.e(e, "Error during getToken")
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun deleteToken(): Outcome<Failure, None> {
        return try {
            messaging.deleteToken().await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error during deleteToken")
            Outcome.Failure(e.toDomain())
        }
    }

}
