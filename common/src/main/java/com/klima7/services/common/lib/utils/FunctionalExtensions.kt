package com.klima7.services.common.lib.utils

fun String?.nullifyBlank(): String? {
    return this?.trim()?.let {
        if(isBlank()) null else it
    }
}
