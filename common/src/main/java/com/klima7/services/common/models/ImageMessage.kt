package com.klima7.services.common.models

import java.util.*

class ImageMessage(
    author: MessageAuthor,
    sendTime: Date,
    val imageUrl: String
): Message(author, sendTime) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as ImageMessage

        if (imageUrl != other.imageUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + imageUrl.hashCode()
        return result
    }

    override fun toString(): String {
        return "ImageMessage(imageUrl='$imageUrl')"
    }

}