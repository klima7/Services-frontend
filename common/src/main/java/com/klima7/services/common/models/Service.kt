package com.klima7.services.common.models

data class Service(
    val id: String,
    val categoryId: String,
    val name: String,
    val constrained: Boolean,
) {

    fun toSimpleService(): SimpleService {
        return SimpleService(id, name)
    }

}