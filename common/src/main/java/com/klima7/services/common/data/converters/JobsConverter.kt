package com.klima7.services.common.data.converters

import com.klima7.services.common.data.entities.JobEntity
import com.klima7.services.common.models.Job
import com.klima7.services.common.models.Location

fun JobEntity.toDomain(id: String): Job {
    return Job(
        id,
        clientId,
        clientName,
        creation.toDate(),
        description,
        location?.toDomain(),
        realizationTime,
        serviceName,
        serviceId,
        active
    )
}

private fun JobEntity.LocationEntity.toDomain(): Location {
    return Location(locationName, locationId, coordinates.toDomain())
}
