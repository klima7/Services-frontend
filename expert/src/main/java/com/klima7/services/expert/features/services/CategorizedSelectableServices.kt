package com.klima7.services.expert.features.services

import com.klima7.services.common.models.Category

data class CategorizedSelectableServices(
    val category: Category,
    val services: List<SelectableService>
)