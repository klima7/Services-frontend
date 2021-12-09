package com.klima7.services.common.models

import java.util.*

class RatingMessage(
    author: MessageAuthor,
    sendTime: Date,
    val ratingId: String,
    val rating: Double,
): Message(author, sendTime) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as RatingMessage

        if (ratingId != other.ratingId) return false
        if (rating != other.rating) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + ratingId.hashCode()
        result = 31 * result + rating.hashCode()
        return result
    }

    override fun toString(): String {
        return "RatingMessage(ratingId='$ratingId', rating=$rating) ${super.toString()}"
    }

}