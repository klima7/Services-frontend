package com.klima7.services.common.models

import java.util.*

data class LastLocation(
    val placeId: String,
    val placeName: String,
    val time: Date,
) {
    constructor(simpleLocation: SimpleLocation, time: Date):
            this(simpleLocation.placeId, simpleLocation.placeName, time)

    fun toSimpleLocation(): SimpleLocation {
        return SimpleLocation(placeId, placeName)
    }
}
