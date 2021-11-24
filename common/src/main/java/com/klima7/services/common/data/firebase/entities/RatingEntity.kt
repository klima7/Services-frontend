package com.klima7.services.common.data.firebase.entities

import com.google.firebase.Timestamp

data class RatingEntity(
    var clientName: String = "",
    var comment: String? = null,
    var date: Timestamp = Timestamp.now(),
    var expertId: String = "",
    var offerId: String = "",
    var rating: Double = 0.0,
    var serviceName: String = "",
)

