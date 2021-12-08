package com.klima7.services.common.extensions

import android.content.Context
import android.util.TypedValue

fun Context.getAttrColor(id: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    return typedValue.data
}
