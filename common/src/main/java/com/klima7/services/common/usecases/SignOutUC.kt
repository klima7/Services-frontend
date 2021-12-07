package com.klima7.services.common.usecases

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.TokensRepository
import com.klima7.services.common.data.repositories.TokensStorageRepository
import com.klima7.services.common.models.Failure

class SignOutUC(
    private val authRepository: AuthRepository,
    private val tokensRepository: TokensRepository,
    private val tokensStorageRepository: TokensStorageRepository,
): BaseUC<None, None>() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        authRepository.signOut()
        val deleteResult = deleteToken()
        if(deleteResult.isFailure) {
            return deleteResult
        }
        return tokensRepository.deleteToken()
    }

    private suspend fun deleteToken(): Outcome<Failure, None> {
        return tokensRepository.getToken().foldS({ failure ->
            Outcome.Failure(failure)
        },
        { token ->
            tokensStorageRepository.deleteToken(token)
        })
    }
}