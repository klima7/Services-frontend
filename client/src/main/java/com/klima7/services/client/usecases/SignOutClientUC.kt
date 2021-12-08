package com.klima7.services.client.usecases

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.TokensRepository
import com.klima7.services.common.data.repositories.TokensStorageRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role
import com.klima7.services.common.usecases.SignOutUC

class SignOutClientUC(
    authRepository: AuthRepository,
    tokensRepository: TokensRepository,
    private val tokensStorageRepository: TokensStorageRepository,
): SignOutUC(authRepository, tokensRepository) {

    override suspend fun deleteToken(token: String): Outcome<Failure, None> {
        return tokensStorageRepository.deleteToken(Role.CLIENT, token)
    }

}