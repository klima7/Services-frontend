package com.klima7.services.client.features.splash

import com.klima7.services.common.components.splash.GetCurrentUserStateUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ClientsRepository
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.UserState

class GetCurrentClientStateUC(
    private val authRepository: AuthRepository,
    private val clientsRepository: ClientsRepository
): GetCurrentUserStateUC() {

    override suspend fun execute(params: None): Outcome<Failure, UserState> {
        return getUidPart()
    }

    private suspend fun getUidPart(): Outcome<Failure, UserState> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null) {
                Outcome.Success(UserState.NOT_LOGGED)
            } else {
                getExpertPart(uid)
            }
        })
    }

    private suspend fun getExpertPart(uid: String): Outcome<Failure, UserState> {
        return clientsRepository.getClient(uid).foldS({ failure ->
            when(failure) {
                Failure.NotFoundFailure -> createClientPart(uid)
                else -> Outcome.Failure(failure)
            }
        }, { client ->
            analyseClientPart(client)
        })
    }

    private suspend fun createClientPart(uid: String): Outcome<Failure, UserState> {
        return clientsRepository.createClientAccount().foldS({ failure ->
            Outcome.Failure(failure)
        }, {
            getExpertPart(uid)
        })
    }

    private fun analyseClientPart(client: Client): Outcome<Failure, UserState> {
        return if(isClientReady(client)) {
            Outcome.Success(UserState.READY)
        } else if(!client.fromCache) {
            Outcome.Success(UserState.NOT_READY)
        } else {
            Outcome.Failure(Failure.InternetFailure)
        }
    }

    private fun isClientReady(client: Client): Boolean {
        return client.info.name != null
    }

}