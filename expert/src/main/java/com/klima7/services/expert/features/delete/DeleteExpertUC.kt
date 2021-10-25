package com.klima7.services.expert.features.delete

import com.klima7.services.common.components.delete.BaseDeleteUserUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Failure

class DeleteExpertUC(
    private val expertsRepository: ExpertsRepository,
    private val authRepository: AuthRepository
): BaseDeleteUserUC() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        return deleteExpertPart()
    }

    private suspend fun deleteExpertPart(): Outcome<Failure, None> {
        return expertsRepository.deleteAccount().foldS({ failure ->
            Outcome.Failure(failure)
        }, {
            signOutPart()
        })
    }

    private suspend fun signOutPart(): Outcome<Failure, None> {
        return authRepository.signOut().foldS({ failure ->
            Outcome.Failure(failure)
        }, {
            Outcome.Success(None())
        })
    }

}