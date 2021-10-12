package com.klima7.services.common.lib.converters

import com.google.android.gms.maps.model.LatLng
import com.klima7.services.common.domain.models.Coordinates

fun Coordinates.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}