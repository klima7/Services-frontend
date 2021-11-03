package com.klima7.services.common.data.entities

import com.google.firebase.Timestamp

data class OfferEntity(
    var creationTime: Timestamp = Timestamp.now(),
    var isPreferred: Boolean = false,
    var ratingId: String? = null,
    var status: Int = 0,
    var archived: Boolean = false,
    var jobId: String = "",

    var clientReadTime: Timestamp = Timestamp.now(),
    var expertReadTime: Timestamp = Timestamp.now(),

    var serviceId: String = "",
    var serviceName: String = "",

    var clientId: String = "",
    var clientName: String = "",

    var expertId: String = "",
    var expertName: String = "",
)

