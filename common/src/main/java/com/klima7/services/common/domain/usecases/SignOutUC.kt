package com.klima7.services.common.domain.usecases

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome

class SignOutUC(
    private val authRepository: AuthRepository
): BaseUC<None, None>() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        authRepository.signOut()
        return Outcome.Success(None())
    }

}