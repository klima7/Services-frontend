package com.klima7.services.common.usecases

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome

class SignOutUC(
    private val authRepository: AuthRepository
): BaseUC<None, None>() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        authRepository.signOut()
        return Outcome.Success(None())
    }

}