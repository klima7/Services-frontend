package com.klima7.services.common.extensions

fun String?.nullifyBlank(): String? {
    return this?.trim()?.let {
        if(isBlank()) null else it
    }
}

fun String.uppercaseFirst(): String {
    if(isEmpty())
        return this
    val first = this[0].uppercaseChar()
    return first + this.substring(1)
}
