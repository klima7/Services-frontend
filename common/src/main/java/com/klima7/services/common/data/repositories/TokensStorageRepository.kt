package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role

class TokensStorageRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun updateToken(role: Role, token: String): Outcome<Failure, None> {
        return firebase.tokensStorageDao.updateToken(role, token)
    }

    suspend fun deleteToken(role: Role, token: String): Outcome<Failure, None> {
        return firebase.tokensStorageDao.deleteToken(role, token)
    }

}