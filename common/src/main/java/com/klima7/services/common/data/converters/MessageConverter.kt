package com.klima7.services.common.data.converters

import com.klima7.services.common.data.entities.MessageEntity
import com.klima7.services.common.models.ImageMessage
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.MessageAuthor
import com.klima7.services.common.models.TextMessage
import java.util.*

fun MessageEntity.toDomain(pendingWrite: Boolean): Message {

    val author = if(author == "expert") MessageAuthor.EXPERT else MessageAuthor.CLIENT
    val sendTime = time?.toDate() ?: Date()

    return when(type) {
        0 -> TextMessage(author, sendTime, message ?: "", pendingWrite)
        1 -> ImageMessage(author, sendTime, imageUrl ?: "")
        else -> TextMessage(author, sendTime,"Error", false)
    }
}
