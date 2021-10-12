package com.klima7.services.expert.features.location

import com.google.android.gms.maps.model.LatLng

data class ChangedLocation(
    val id: String,
    val name: String,
    val coords: LatLng
)