package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role

class TokensRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun getToken(): Outcome<Failure, String> {
        return firebase.tokensDao.getToken()
    }

    suspend fun deleteToken(): Outcome<Failure, None> {
        return firebase.tokensDao.deleteToken()
    }

    suspend fun updateStoredToken(role: Role, token: String): Outcome<Failure, None> {
        return firebase.tokensStorageDao.updateToken(role, token)
    }

    suspend fun deleteStoredToken(role: Role, token: String): Outcome<Failure, None> {
        return firebase.tokensStorageDao.deleteToken(role, token)
    }

}