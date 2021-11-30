package com.klima7.services.common.models

data class AllAndSelectedServices(
    val all: Set<CategoryWithServices>,
    val selectedIds: Set<String>
)
