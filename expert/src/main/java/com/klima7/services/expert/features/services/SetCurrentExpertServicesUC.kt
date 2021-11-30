package com.klima7.services.expert.features.services

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service

class SetCurrentExpertServicesUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<SetCurrentExpertServicesUC.Params, None>() {

    data class Params(val services: List<Service>)

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        val servicesIds = params.services.map { it.id }
        return expertsRepository.setServicesIds(servicesIds)
    }

}