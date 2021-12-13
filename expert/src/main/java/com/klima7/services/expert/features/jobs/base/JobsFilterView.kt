package com.klima7.services.expert.features.jobs.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ViewJobFilterBinding

class JobsFilterView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewJobFilterBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_job_filter, this, true)
        refreshView()
    }

    private fun refreshView() {

    }
}
