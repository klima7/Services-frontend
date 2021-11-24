package com.klima7.services.common.data.firebase.converters

import com.google.firebase.firestore.GeoPoint
import com.klima7.services.common.models.Coordinates

fun GeoPoint.toDomain(): Coordinates {
    return Coordinates(this.latitude, this.longitude)
}
