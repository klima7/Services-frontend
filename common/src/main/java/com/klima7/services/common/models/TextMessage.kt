package com.klima7.services.common.models

import java.util.*

class TextMessage(
    author: MessageAuthor,
    sendTime: Date,
    val text: String,
    val pending: Boolean,
): Message(author, sendTime) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as TextMessage

        if (text != other.text) return false
        if (pending != other.pending) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + pending.hashCode()
        return result
    }

    override fun toString(): String {
        return "TextMessage(text='$text', pending=$pending) ${super.toString()}"
    }

}