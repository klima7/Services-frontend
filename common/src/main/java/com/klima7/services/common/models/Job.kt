package com.klima7.services.common.models

import java.util.*

data class Job(
    val id: String,
    val clientId: String?,
    val clientName: String,
    val creationDate: Date,
    val description: String,
    val location: Location?,
    val realizationTime: String,
    val serviceName: String,
    val serviceId: String,
    val active: Boolean,
    val finishDate: Date?,
    val unreadOffersCount: Int,
)
