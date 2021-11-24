package com.klima7.services.common.models

import java.util.*

data class LastLocation(
    val id: Int,
    val placeId: String,
    val placeName: String,
    val time: Date
)