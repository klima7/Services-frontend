package com.klima7.services.common.data.sources.firebase.converters

import com.klima7.services.common.data.sources.firebase.entities.OfferEntity
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus

fun OfferEntity.toDomain(id: String): Offer {
    val offerStatus = when(status) {
        0 -> OfferStatus.NEW
        1 -> OfferStatus.REJECTED
        2 -> OfferStatus.IN_REALIZATION
        3 -> OfferStatus.DONE
        else -> OfferStatus.NEW
    }
    return Offer(id, creationTime.toDate(), ratingId, offerStatus, archived, jobId,
        clientReadTime.toDate(), expertReadTime.toDate(), serviceId, serviceName, clientId,
        clientName, expertId, expertName)
}
