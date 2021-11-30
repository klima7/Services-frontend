package com.klima7.services.common.models

data class CategoryWithServices(
    val category: Category,
    val services: List<Service>
)
