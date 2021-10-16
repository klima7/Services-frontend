package com.klima7.services.common.ui.converters

import com.google.android.gms.maps.model.LatLng
import com.klima7.services.common.domain.models.Coordinates

fun Coordinates.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}