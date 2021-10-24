package com.klima7.services.client.features.setup

import com.klima7.services.client.usecases.GetCurrentClientUC
import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.Failure

class GetCurrentClientSetupStateUC(
    private val getCurrentClientUC: GetCurrentClientUC
): BaseUC<None, ClientSetupState>() {

    override suspend fun execute(params: None): Outcome<Failure, ClientSetupState> {
        return getClientPart()
    }

    private suspend fun getClientPart(): Outcome<Failure, ClientSetupState> {
        return getCurrentClientUC.run(None()).foldS({ failure ->
            Outcome.Failure(failure)
        }, { client ->
            if(client.fromCache) {
                Outcome.Failure(Failure.InternetFailure)
            }
            else {
                analyseClientPart(client)
            }
        })
    }

    private fun analyseClientPart(client: Client): Outcome<Failure, ClientSetupState> {
        val infoConfigured = client.info.name != null
        return Outcome.Success(ClientSetupState(infoConfigured))
    }

}