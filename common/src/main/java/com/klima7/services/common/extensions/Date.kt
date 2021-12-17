package com.klima7.services.common.extensions

import android.text.format.DateUtils
import java.util.*


fun Date.applyTimezone(): Date {
    return this
}

fun Date.getTimeAgo(): String {
    return DateUtils.getRelativeTimeSpanString(
        this.time,
        System.currentTimeMillis(),
        1, DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString()
}

fun Date.getPrettyTime(): String {
    return getTimeAgo()
}