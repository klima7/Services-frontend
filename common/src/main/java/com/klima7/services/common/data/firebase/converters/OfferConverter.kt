package com.klima7.services.common.data.firebase.converters

import com.klima7.services.common.data.firebase.entities.OfferEntity
import com.klima7.services.common.extensions.applyTimezone
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus

fun OfferEntity.toDomain(id: String): Offer {
    val offerStatus = when(status) {
        0 -> OfferStatus.NEW
        1 -> OfferStatus.CANCELLED
        2 -> OfferStatus.IN_REALIZATION
        3 -> OfferStatus.DONE
        else -> OfferStatus.NEW
    }
    return Offer(id, creationTime.toDate().applyTimezone(), ratingId, offerStatus, archived, jobId,
        clientReadTime?.toDate()?.applyTimezone(), expertReadTime?.toDate()?.applyTimezone(),
        lastMessage?.toDomain(false),
        serviceId, serviceName, clientId,
        clientName, expertId, expertName)
}
