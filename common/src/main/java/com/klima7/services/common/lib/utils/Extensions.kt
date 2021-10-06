package com.klima7.services.common.lib.utils

import android.view.View
import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(containerId: Int, fragment: Fragment) {
    childFragmentManager
        .beginTransaction()
        .add(containerId, fragment)
        .commit()
}