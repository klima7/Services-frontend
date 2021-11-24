package com.klima7.services.common.data.sources.firebase.converters

import com.klima7.services.common.data.sources.firebase.entities.ClientEntity
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.ClientInfo

fun ClientEntity.toDomain(id: String, fromCache: Boolean): Client {
    val info = ClientInfo(info.name, info.phone)
    return Client(id, info, fromCache)
}
