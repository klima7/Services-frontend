package com.klima7.services.common.components.login

import android.util.Log
import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.TokensRepository
import com.klima7.services.common.data.repositories.TokensStorageRepository
import com.klima7.services.common.models.Failure

class CompleteLoginUC(
    private val tokensRepository: TokensRepository,
    private val tokensStorageRepository: TokensStorageRepository
): BaseUC<None, None>() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        Log.i("Hello", "Completing login")
        return tokensRepository.getToken().foldS(
            { failure ->
                Outcome.Failure(failure)
            }, { token ->
                return@foldS tokensStorageRepository.updateToken(token)
            }
        )
    }
}
