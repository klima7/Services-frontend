package com.klima7.services.common.messaging

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.data.repositories.TokensRepository
import com.klima7.services.common.data.repositories.TokensStorageRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role

abstract class BaseUpdateTokenUC(
    private val role: Role
): BaseUC<None, None>() {

    private val tokensRepository = TokensRepository(FirebaseSource())
    private val tokensStorageRepository = TokensStorageRepository(FirebaseSource())

    override suspend fun execute(params: None): Outcome<Failure, None> {
        return getTokenPart()
    }

    private suspend fun getTokenPart(): Outcome<Failure, None> {
        return tokensRepository.getToken().foldS({ failure ->
            Outcome.Failure(failure)
        }, { token ->
            refreshTokenPart(token)
        })
    }

    private suspend fun refreshTokenPart(token: String): Outcome<Failure, None> {
        return tokensStorageRepository.updateToken(role, token)
    }
}