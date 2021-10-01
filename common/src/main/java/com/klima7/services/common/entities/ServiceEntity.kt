package com.klima7.services.common.entities

import com.klima7.services.common.models.Service

data class ServiceEntity(
    var id: String = "",
    var name: String = "",
    var categoryId: String = "",
    var categoryName: String = ""
)

fun ServiceEntity.toDomain(): Service {
    return Service(id, name, categoryId, categoryName)
}
