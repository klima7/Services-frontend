package com.klima7.services.common.data.entities

import com.google.firebase.Timestamp

data class MessageEntity(
    var author: String = "",
    var time: Timestamp? = Timestamp.now(),
    var type: Int = 0,

    // Optional
    var message: String? = null,
    var imageUrl: String? = null
)

