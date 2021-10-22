package com.klima7.services.common.models

import java.util.*

data class Rating(
    val id: String,
    val clientName: String,
    val serviceName: String,
    val comment: String?,
    val rating: Double,
    val date: Date,
    val offerId: String,
    val expertId: String,
)
