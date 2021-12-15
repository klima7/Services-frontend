package com.klima7.services.common.components.login

import android.util.Log
import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.TokensRepository
import com.klima7.services.common.models.Failure

abstract class CompleteLoginUC(
    private val tokensRepository: TokensRepository,
): BaseUC<None, None>() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        Log.i("Hello", "Completing login")
        return deleteTokenPart()
    }

    private suspend fun deleteTokenPart(): Outcome<Failure, None> {
        return tokensRepository.deleteToken().foldS({
            Outcome.Failure(Failure.InternetFailure)
        }, {
            getTokenPart()
        })
    }

    private suspend fun getTokenPart(): Outcome<Failure, None> {
        return tokensRepository.getToken().foldS(
            {
                Outcome.Failure(Failure.InternetFailure)
            }, { token ->
                updateTokenInStoragePart(token)
            }
        )
    }

    abstract suspend fun updateTokenInStoragePart(token: String): Outcome<Failure, None>
}
