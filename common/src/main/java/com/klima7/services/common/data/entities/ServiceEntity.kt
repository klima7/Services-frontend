package com.klima7.services.common.data.entities

import com.klima7.services.common.domain.models.Service

data class ServiceEntity(
    var name: String = "",
    var categoryId: String = "",
)

fun ServiceEntity.toDomain(id: String): Service {
    return Service(id, categoryId, name)
}
