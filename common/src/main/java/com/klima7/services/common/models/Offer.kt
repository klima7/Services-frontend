package com.klima7.services.common.models

import java.util.*

data class Offer(
    val creationTime: Date,
    val isPreferred: Boolean,
    val ratingId: String?,
    val status: OfferStatus,

    val clientReadTime: Date,
    val expertReadTime: Date,

    val serviceId: String,
    val serviceName: String,

    val clientId: String,
    val clientName: String,

    val expertId: String,
    val expertName: String,
)
