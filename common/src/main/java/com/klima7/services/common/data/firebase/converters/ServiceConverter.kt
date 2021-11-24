package com.klima7.services.common.data.firebase.converters

import com.klima7.services.common.data.firebase.entities.ServiceEntity
import com.klima7.services.common.models.Service

fun ServiceEntity.toDomain(id: String): Service {
    return Service(id, categoryId, name, locationConstrained)
}
