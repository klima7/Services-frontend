package com.klima7.services.common.util

import com.google.firebase.firestore.GeoPoint
import com.klima7.services.common.domain.models.Coordinates

fun GeoPoint.toDomain(): Coordinates {
    return Coordinates(this.longitude, this.latitude)
}
