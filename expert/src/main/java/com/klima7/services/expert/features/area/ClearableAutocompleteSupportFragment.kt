package com.klima7.services.expert.features.area

import android.os.Bundle
import android.view.View
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.klima7.services.expert.R

class ClearableAutocompleteSupportFragment: AutocompleteSupportFragment() {

    private var listener: OnClearListener? = null

    override fun onViewCreated(p0: View, p1: Bundle?) {
        super.onViewCreated(p0, p1)
        // Warning: This code rely on internal code of Places library
        val clearButton = requireView().findViewById<View>(R.id.places_autocomplete_clear_button)
        clearButton.setOnClickListener {
            setText("")
            listener?.onClear()
        }
    }

    fun setOnPlaceClearedListener(listener: OnClearListener) {
        this.listener = listener
    }

    interface OnClearListener {
        fun onClear()
    }

}
