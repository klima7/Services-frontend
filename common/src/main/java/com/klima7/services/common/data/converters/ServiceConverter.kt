package com.klima7.services.common.data.converters

import com.klima7.services.common.data.entities.ServiceEntity
import com.klima7.services.common.models.Service

fun ServiceEntity.toDomain(id: String): Service {
    return Service(id, categoryId, name)
}
