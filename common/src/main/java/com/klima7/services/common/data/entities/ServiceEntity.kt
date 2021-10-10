package com.klima7.services.common.data.entities

import com.klima7.services.common.domain.models.Service

data class ServiceEntity(
    var id: String = "",
    var name: String = "",
    var categoryId: String = "",
    var categoryName: String = ""
)

fun ServiceEntity.toDomain(): Service {
    return Service(id, name, categoryId)
}
