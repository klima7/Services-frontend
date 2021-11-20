package com.klima7.services.common.ui

import com.klima7.services.common.data.di.EMULATE

object UrlUtils {

    fun fixUrlForEmulator(url: String): String {
        return if(EMULATE) url.replace("localhost", "10.0.2.2") else url
    }
}