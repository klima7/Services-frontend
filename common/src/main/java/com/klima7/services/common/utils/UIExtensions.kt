package com.klima7.services.common.utils

import android.widget.Toast
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

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun Fragment.showShortToast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}