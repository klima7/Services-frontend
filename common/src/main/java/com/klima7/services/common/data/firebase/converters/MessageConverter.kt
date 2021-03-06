package com.klima7.services.common.data.firebase.converters

import com.klima7.services.common.data.firebase.entities.MessageEntity
import com.klima7.services.common.extensions.applyTimezone
import com.klima7.services.common.models.*
import java.util.*

fun MessageEntity.toDomain(pendingWrite: Boolean): Message {

    val author = if(author == "expert") MessageAuthor.EXPERT else MessageAuthor.CLIENT
    val sendTime = time?.toDate()?.applyTimezone() ?: Date()

    return when(type) {
        0 -> TextMessage(author, sendTime, message ?: "", pendingWrite)
        1 -> ImageMessage(author, sendTime, imageUrl ?: "")
        2 -> {
            val offerStatus = when(newStatus) {
                0 -> OfferStatus.NEW
                1 -> OfferStatus.CANCELLED
                2 -> OfferStatus.IN_REALIZATION
                3 -> OfferStatus.DONE
                else -> OfferStatus.NEW
            }
            StatusChangeMessage(author, sendTime, offerStatus)
        }
        3 -> RatingMessage(author, sendTime, ratingId ?: "", rating ?: .0)
        else -> TextMessage(author, sendTime,"Error", false)
    }
}
