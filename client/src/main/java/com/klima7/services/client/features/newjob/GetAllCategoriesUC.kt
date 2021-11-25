package com.klima7.services.client.features.newjob

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.models.Category

class GetAllCategoriesUC(
    private val servicesRepository: ServicesRepository
): BaseUC<None, List<Category>>() {

    override suspend fun execute(params: None) = servicesRepository.getAllCategories()
}