package com.klima7.services.common.extensions

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue

fun Context.getAttrColor(id: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    return typedValue.data
}

fun Context.isLarge(): Boolean {
    return ((this.resources.configuration.screenLayout
            and Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE)
}