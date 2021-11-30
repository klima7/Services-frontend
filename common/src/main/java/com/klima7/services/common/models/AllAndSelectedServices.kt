package com.klima7.services.common.models

data class AllAndSelectedServices(
    val all: List<CategoryWithServices>,
    val selectedIds: Set<String>
)
