package com.klima7.services.common.entities

import com.google.firebase.firestore.GeoPoint
import com.klima7.services.common.util.toDomain
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.ExpertInfo
import com.klima7.services.common.models.NamedLocation
import com.klima7.services.common.models.WorkingArea

data class ExpertEntity(
    var info: InfoEntity = InfoEntity(),
    var workingArea: WorkingAreaEntity = WorkingAreaEntity(),
    var services: List<ServiceEntity> = listOf(),
    var profileImage: String? = null,

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
        var radius: Int = 0
    )

}

fun ExpertEntity.toDomain(fromCache: Boolean): Expert {
    val info = ExpertInfo(info.name, info.company, info.description, info.phone, info.email, info.website)
    val nl = NamedLocation(workingArea.locationName, workingArea.coordinates.toDomain())
    val wa = WorkingArea(nl, workingArea.radius)
    val s = services.map { it.toDomain() }.toSet()
    return Expert(info, profileImage, wa, s, ratingsCount, commentsCount, rating, active, fromCache)
}
