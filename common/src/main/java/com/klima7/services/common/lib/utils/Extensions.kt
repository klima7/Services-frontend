package com.klima7.services.common.lib.utils

import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(containerId: Int, fragment: Fragment) {
    childFragmentManager
        .beginTransaction()
        .add(containerId, fragment)
        .commit()
}

fun String?.nullifyBlank(): String? {
    return this?.trim()?.let {
        if(isBlank()) null else it
    }
}
