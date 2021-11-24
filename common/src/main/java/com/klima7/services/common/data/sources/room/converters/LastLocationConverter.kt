package com.klima7.services.common.data.sources.room.converters

import com.klima7.services.common.data.sources.room.entities.LastLocationEntity
import com.klima7.services.common.models.LastLocation
import java.util.*

fun LastLocation.toRoomEntity(): LastLocationEntity {
    return LastLocationEntity(placeId, placeName, time.time)
}

fun LastLocationEntity.toDomain(): LastLocation {
    return LastLocation(placeId, placeName, Date(time))
}
