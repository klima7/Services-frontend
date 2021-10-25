package com.klima7.services.client.features.delete

import com.klima7.services.common.components.delete.BaseDeleteUserUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ClientsRepository
import com.klima7.services.common.models.Failure

class DeleteClientUC(
    private val clientsRepository: ClientsRepository,
    private val authRepository: AuthRepository
): BaseDeleteUserUC() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        return deleteClientPart()
    }

    private suspend fun deleteClientPart(): Outcome<Failure, None> {
        return clientsRepository.deleteAccount().foldS({ failure ->
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