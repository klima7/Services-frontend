package com.klima7.services.expert.features.services

import com.klima7.services.common.domain.models.Category
import com.klima7.services.common.domain.models.Service

data class CategorizedServices(
    val category: Category,
    val services: List<Service>
)