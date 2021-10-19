package com.klima7.services.common.ui.profile.services

import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.Outcome

class GetServicesFromIds(
    private val servicesRepository: ServicesRepository
): BaseUC<GetServicesFromIds.Params, List<Service>>() {

    data class Params(val ids: List<String>)

    override suspend fun execute(params: Params): Outcome<Failure, List<Service>> {
        return getServicesPart(params.ids)
    }

    private suspend fun getServicesPart(ids: List<String>): Outcome<Failure, List<Service>> {
        val services = mutableListOf<Service>()
        ids.forEach { id ->
            val outcome = servicesRepository.getService(id)
            if(outcome is Outcome.Failure)
                return Outcome.Failure(outcome.a)
            else if(outcome is Outcome.Success)
                services.add(outcome.b)
        }
        return Outcome.Success(services)
    }

}