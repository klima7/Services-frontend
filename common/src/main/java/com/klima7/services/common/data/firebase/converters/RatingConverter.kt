package com.klima7.services.common.data.firebase.converters

import com.klima7.services.common.data.firebase.entities.RatingEntity
import com.klima7.services.common.extensions.applyTimezone
import com.klima7.services.common.models.Rating

fun RatingEntity.toDomain(id: String): Rating {
    return Rating(id, clientName, serviceName, comment, rating, date.toDate().applyTimezone(),
        offerId, expertId)
}
