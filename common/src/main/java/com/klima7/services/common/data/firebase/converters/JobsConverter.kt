package com.klima7.services.common.data.firebase.converters

import com.klima7.services.common.data.firebase.entities.JobEntity
import com.klima7.services.common.extensions.applyTimezone
import com.klima7.services.common.models.Job
import com.klima7.services.common.models.Location

fun JobEntity.toDomain(id: String): Job {
    return Job(
        id,
        clientId,
        clientName,
        creation.toDate().applyTimezone(),
        description,
        location?.toDomain(),
        realizationTime,
        serviceName,
        serviceId,
        active,
        finishDate.toDate().applyTimezone(),
        unreadOffers.size,
    )
}

private fun JobEntity.LocationEntity.toDomain(): Location {
    return Location(locationName, locationId, coordinates.toDomain())
}
