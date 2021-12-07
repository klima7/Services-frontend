package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure

class TokensStorageRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun updateToken(token: String): Outcome<Failure, None> {
        return firebase.tokensStorageDao.updateToken(token)
    }

    suspend fun deleteToken(token: String): Outcome<Failure, None> {
        return firebase.tokensStorageDao.deleteToken(token)
    }

}