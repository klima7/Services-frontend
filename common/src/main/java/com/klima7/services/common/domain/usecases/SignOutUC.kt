package com.klima7.services.common.domain.usecases

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.Outcome

class SignOutUC(
    private val authRepository: AuthRepository
): BaseUC<BaseUC.NoParams, BaseUC.NoResult>() {

    override suspend fun execute(params: NoParams): Outcome<Failure, NoResult> {
        authRepository.signOut()
        return Outcome.Success(NoResult())
    }

}