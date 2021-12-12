package com.klima7.services.common.models

import java.util.*

data class Offer(
    val id: String,
    val creationTime: Date,
    val ratingId: String?,
    val status: OfferStatus,
    val archived: Boolean,
    val jobId: String,

    val clientReadTime: Date?,
    val expertReadTime: Date?,

    val lastMessage: Message?,

    val serviceId: String,
    val serviceName: String,

    val clientId: String?,
    val clientName: String,

    val expertId: String?,
    val expertName: String,
)
