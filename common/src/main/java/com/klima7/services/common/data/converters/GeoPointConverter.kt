package com.klima7.services.common.data.converters

import com.google.firebase.firestore.GeoPoint
import com.klima7.services.common.domain.models.Coordinates

fun GeoPoint.toDomain(): Coordinates {
    return Coordinates(this.longitude, this.latitude)
}
