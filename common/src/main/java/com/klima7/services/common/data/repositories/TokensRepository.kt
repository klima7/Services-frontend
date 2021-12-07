package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure

class TokensRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun getToken(): Outcome<Failure, String> {
        return firebase.tokensDao.getToken()
    }

    suspend fun deleteToken(): Outcome<Failure, None> {
        return firebase.tokensDao.deleteToken()
    }

}