package com.klima7.services.common.lib.utils

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun Fragment.replaceFragment(containerId: Int, fragment: Fragment) {
    childFragmentManager
        .beginTransaction()
        .add(containerId, fragment)
        .commit()
}

fun ViewGroup.setEnabledRecursive(enabled: Boolean) {
    this.isEnabled = enabled
    for (i in 0 until this.childCount) {
        val child = this.getChildAt(i)
        if (child is ViewGroup) {
            child.setEnabledRecursive(enabled)
        } else {
            child.isEnabled = enabled
        }
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}