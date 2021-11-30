package com.klima7.services.expert.features.offer

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.data.repositories.ClientsRepository
import com.klima7.services.common.models.Client

class GetClientUC(
    private val clientsRepository: ClientsRepository,
): BaseUC<GetClientUC.Params, Client>() {

    data class Params(val uid: String)

    override suspend fun execute(params: Params) = clientsRepository.getClient(params.uid)
}