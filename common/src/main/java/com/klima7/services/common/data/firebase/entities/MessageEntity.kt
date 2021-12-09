package com.klima7.services.common.data.firebase.entities

import com.google.firebase.Timestamp

data class MessageEntity(
    var author: String = "",
    var time: Timestamp? = Timestamp.now(),
    var type: Int = 0,

    // Optional
    var message: String? = null,
    var imageUrl: String? = null,
    var newStatus: Int? = null,
    var ratingId: String? = null,
    var rating: Double? = null,
)

