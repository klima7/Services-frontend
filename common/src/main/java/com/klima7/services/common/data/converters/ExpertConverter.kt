package com.klima7.services.common.data.converters

import com.klima7.services.common.data.entities.ExpertEntity
import com.klima7.services.common.models.*

fun ExpertEntity.toDomain(id: String, fromCache: Boolean): Expert {
    val info = ExpertInfo(info.name, info.company, info.description, info.phone, info.email, info.website)
    val wa = workingArea?.let {
        val nl = Location(it.locationName, it.locationId, it.coordinates.toDomain())
        WorkingArea(nl, it.radius)
    }
    val pi = profileImage?.toDomain()
    val s = services.toSet()
    return Expert(id, info, pi, wa, s, ratingsCount, commentsCount, rating, active, fromCache)
}
