package com.klima7.services.common.models

import java.util.*

abstract class Message(
    val author: MessageAuthor,
    val sendTime: Date,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (author != other.author) return false
        if (sendTime != other.sendTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = author.hashCode()
        result = 31 * result + sendTime.hashCode()
        return result
    }

    override fun toString(): String {
        return "Message(author=$author, sendTime=$sendTime)"
    }

}