package com.klima7.services.common.data.entities

import com.klima7.services.common.domain.models.Category

data class CategoryEntity(
    var name: String = "",
)

fun CategoryEntity.toDomain(id: String): Category {
    return Category(id, name)
}
