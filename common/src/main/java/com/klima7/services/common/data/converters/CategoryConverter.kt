package com.klima7.services.common.data.converters

import com.klima7.services.common.data.entities.CategoryEntity
import com.klima7.services.common.domain.models.Category

fun CategoryEntity.toDomain(id: String): Category {
    return Category(id, name)
}
