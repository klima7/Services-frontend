package com.klima7.services.expert.features.services

import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.base.BaseUC
import com.klima7.services.common.utils.None
import com.klima7.services.common.utils.Outcome

class SetCurrentExpertServices(
    private val expertsRepository: ExpertsRepository
): BaseUC<SetCurrentExpertServices.Params, None>() {

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        val servicesIds = params.services.map { it.id }
        return expertsRepository.setServicesIds(servicesIds)
    }

    data class Params(val services: List<Service>)
}