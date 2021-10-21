package com.klima7.services.common.domain.models

import java.util.*

data class Comment(
    val clientName: String,
    val serviceName: String,
    val comment: String,
    val rating: Double,
    val date: Date,
    val offerId: String,
    val expertId: String,
)
