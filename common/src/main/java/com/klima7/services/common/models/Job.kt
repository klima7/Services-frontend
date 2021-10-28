package com.klima7.services.common.models

import java.util.*

data class Job(
    val id: String,
    val clientId: String,
    val clientName: String,
    val creationDate: Date,
    val description: String,
    val location: Location?,
    val preferredExpertsIds: List<String>,
    val realizationTime: String,
    val serviceName: String,
    val serviceId: String,
)