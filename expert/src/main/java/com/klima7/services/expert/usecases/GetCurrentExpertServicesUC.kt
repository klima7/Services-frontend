package com.klima7.services.expert.usecases

import com.klima7.services.common.components.profile.services.GetServicesFromIds
import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service

class GetCurrentExpertServicesUC(
    private val getCurrentExpertUC: GetCurrentExpertUC,
    private val getServicesFromIds: GetServicesFromIds
): BaseUC<None, List<Service>>() {

    override suspend fun execute(params: None): Outcome<Failure, List<Service>> {
        return getExpertPart()
    }

    private suspend fun getExpertPart(): Outcome<Failure, List<Service>> {
        return getCurrentExpertUC.run(None()).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            getServicesPart(expert)
        })
    }

    private suspend fun getServicesPart(expert: Expert): Outcome<Failure, List<Service>> {
        return getServicesFromIds.run(
        GetServicesFromIds.Params(expert.servicesIds.toList())).foldS({ failure ->
            Outcome.Failure(failure)
        }, { services ->
            Outcome.Success(services)
        })
    }

}