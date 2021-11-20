package com.klima7.services.common.ui

import com.klima7.services.common.BuildConfig

object UrlUtils {

    fun fixUrlForEmulator(url: String): String {
        return if(BuildConfig.EMULATE) url.replace("localhost", "10.0.2.2") else url
    }
}