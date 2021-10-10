package com.klima7.services.common.data.entities

import com.google.firebase.firestore.GeoPoint
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.ExpertInfo
import com.klima7.services.common.domain.models.NamedLocation
import com.klima7.services.common.domain.models.WorkingArea

data class ExpertEntity(
    var info: InfoEntity = InfoEntity(),
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
        var radius: Int = 0
    )

}

fun ExpertEntity.toDomain(id: String, fromCache: Boolean): Expert {
    val info = ExpertInfo(info.name, info.company, info.description, info.phone, info.email, info.website)
    val wa = workingArea?.let {
        val nl = NamedLocation(it.locationName, it.coordinates.toDomain())
        WorkingArea(nl, it.radius)
    }
    val s = services.toSet()
    return Expert(id, info, wa, s, ratingsCount, commentsCount, rating, active, fromCache)
}
