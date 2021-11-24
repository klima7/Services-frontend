package com.klima7.services.client.features.newjob.service

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.models.Service

class GetServicesFromCategoryUC(
    private val servicesRepository: ServicesRepository,
): BaseUC<GetServicesFromCategoryUC.Params, List<Service>>() {

    data class Params(val categoryId: String)

    override suspend fun execute(params: Params) =
        servicesRepository.getServicesFromCategory(params.categoryId)
}