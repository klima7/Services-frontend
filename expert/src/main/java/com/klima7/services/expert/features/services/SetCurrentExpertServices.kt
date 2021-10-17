package com.klima7.services.expert.features.services

import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome

class SetCurrentExpertServices(
    private val expertsRepository: ExpertsRepository
): BaseUC<SetCurrentExpertServices.Params, None>() {

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        val servicesIds = params.services.map { it.id }
        return expertsRepository.setServicesIds(servicesIds)
    }

    data class Params(val services: List<Service>)
}