package com.klima7.services.expert.features.services

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ViewServicesSelectionListBinding


class ServicesSelectionListView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewServicesSelectionListBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_services_selection_list, this, true)
        refreshView()
    }

    private fun refreshView() {

    }

}
