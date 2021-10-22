package com.klima7.services.common.extensions

fun String?.nullifyBlank(): String? {
    return this?.trim()?.let {
        if(isBlank()) null else it
    }
}
