package com.klima7.services.common.models

import java.util.*

class StatusChangeMessage(
    author: MessageAuthor,
    sendTime: Date,
    val newStatus: OfferStatus,
): Message(author, sendTime) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as StatusChangeMessage

        if (newStatus != other.newStatus) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + newStatus.hashCode()
        return result
    }

    override fun toString(): String {
        return "StatusChangeMessage(newStatus=$newStatus) ${super.toString()}"
    }

}