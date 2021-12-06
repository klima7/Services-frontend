package com.klima7.services.common.models

enum class Role {
    CLIENT,
    EXPERT;

    fun toMessageAuthor(): MessageAuthor {
        return when(this) {
            CLIENT -> MessageAuthor.CLIENT
            EXPERT -> MessageAuthor.EXPERT
        }
    }
}