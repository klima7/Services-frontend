package com.klima7.services.common.data.sources.firebase.converters

import com.klima7.services.common.data.sources.firebase.entities.CategoryEntity
import com.klima7.services.common.models.Category

fun CategoryEntity.toDomain(id: String): Category {
    return Category(id, name)
}
