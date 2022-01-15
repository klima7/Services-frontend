package com.klima7.services.client.features.splash

import com.klima7.services.common.components.login.RefreshTokenUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.TokensRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role

class RefreshClientTokenUC(
    private val tokensRepository: TokensRepository,
): RefreshTokenUC(tokensRepository) {

    override suspend fun updateTokenInStoragePart(token: String): Outcome<Failure, None> {
        return tokensRepository.updateStoredToken(Role.CLIENT, token)
    }
}