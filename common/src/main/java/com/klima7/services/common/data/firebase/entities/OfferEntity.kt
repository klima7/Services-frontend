package com.klima7.services.common.data.firebase.entities

import com.google.firebase.Timestamp
import java.util.*

data class OfferEntity(
    var creationTime: Timestamp = Timestamp.now(),
    var ratingId: String? = null,
    var status: Int = 0,
    var archived: Boolean = false,
    var jobId: String = "",

    var clientReadTime: Timestamp? = Timestamp.now(),
    var expertReadTime: Timestamp? = Timestamp.now(),

    var lastMessage: MessageEntity? = null,

    var serviceId: String = "",
    var serviceName: String = "",

    var clientId: String = "",
    var clientName: String = "",

    var expertId: String = "",
    var expertName: String = "",
)

