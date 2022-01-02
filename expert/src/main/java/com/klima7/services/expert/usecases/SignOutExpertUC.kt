package com.klima7.services.expert.usecases

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.TokensRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role
import com.klima7.services.common.usecases.SignOutUC

class SignOutExpertUC(
    authRepository: AuthRepository,
    private val tokensRepository: TokensRepository,
): SignOutUC(authRepository, tokensRepository) {

    override suspend fun deleteToken(token: String): Outcome<Failure, None> {
        return tokensRepository.deleteStoredToken(Role.EXPERT, token)
    }

}