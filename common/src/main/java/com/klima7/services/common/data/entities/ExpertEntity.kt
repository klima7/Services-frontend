package com.klima7.services.common.data.entities

import com.google.firebase.firestore.GeoPoint

data class ExpertEntity(
    var info: InfoEntity = InfoEntity(),
    var profileImage: ProfileImageEntity? = null,
    var workingArea: WorkingAreaEntity? = null,
    var services: List<String> = listOf(),

    var active: Boolean = false,
    var ready: Boolean = false,

    var commentsCount: Int = 0,
    var ratingsCount: Int = 0,
    var rating: Double = 0.0,
) {

    data class InfoEntity(
        val name: String? = null,
        val company: String? = null,
        val description: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val website: String? = null,
    )

    data class WorkingAreaEntity(
        var coordinates: GeoPoint = GeoPoint(0.0, 0.0),
        var locationName: String = "",
        var locationId: String = "",
        var radius: Int = 0
    )



}

