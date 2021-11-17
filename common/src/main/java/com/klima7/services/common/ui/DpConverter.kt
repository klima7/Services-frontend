package com.klima7.services.common.ui

import android.content.Context
import android.util.DisplayMetrics

fun convertDpToPixel(dp: Float, context: Context): Int {
    val f = dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    return f.toInt()
}
