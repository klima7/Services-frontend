package com.klima7.services.expert.features.services

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.ExpertsRepository

class SetCurrentExpertServicesUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<SetCurrentExpertServicesUC.Params, None>() {

    data class Params(val servicesIds: Set<String>)

    override suspend fun execute(params: Params) =
        expertsRepository.setServicesIds(params.servicesIds.toList())

}