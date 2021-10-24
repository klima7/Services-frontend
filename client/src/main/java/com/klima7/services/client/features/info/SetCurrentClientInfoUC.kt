package com.klima7.services.client.features.info

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.ClientsRepository
import com.klima7.services.common.models.ClientInfo

class SetCurrentClientInfoUC(
    private val clientsRepository: ClientsRepository
): BaseUC<SetCurrentClientInfoUC.Params, None>() {

    override suspend fun execute(params: Params) = clientsRepository.setClientInfo(params.info)

    data class Params(val info: ClientInfo)

}
