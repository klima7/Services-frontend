package com.klima7.services.client.usecases

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ClientsRepository
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.Failure

class GetCurrentClientUC(
    private val authRepository: AuthRepository,
    private val clientsRepository: ClientsRepository
) : BaseUC<None, Client>(){

    override suspend fun execute(params: None): Outcome<Failure, Client> {
        return getUidPart()
    }

    private suspend fun getUidPart(): Outcome<Failure, Client> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null)
                Outcome.Failure(Failure.PermissionFailure)
            else
                getExpertPart(uid)
        })
    }

    private suspend fun getExpertPart(uid: String): Outcome<Failure, Client> {
        return clientsRepository.getClient(uid).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            Outcome.Success(expert)
        })
    }

}