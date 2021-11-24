package com.klima7.services.common.data.sources.firebase.converters

import com.klima7.services.common.data.sources.firebase.entities.RatingEntity
import com.klima7.services.common.models.Rating

fun RatingEntity.toDomain(id: String): Rating {
    return Rating(id, clientName, serviceName, comment, rating, date.toDate(), offerId, expertId)
}
