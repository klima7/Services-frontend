package com.klima7.services.client.features.jobs

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.models.Service

class GetAllServicesUC(
    private val servicesRepository: ServicesRepository
): BaseUC<None, List<Service>>() {

    override suspend fun execute(params: None) = servicesRepository.getAllServices()
}