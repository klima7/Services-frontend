package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewDetailedJobBinding
import com.klima7.services.common.databinding.ViewLoadListBinding
import com.klima7.services.common.models.Job
import java.util.*


class JobView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewDetailedJobBinding
    private var job: Job? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_detailed_job, this, true)
        refreshView()
    }

    fun setJob(job: Job) {
        this.job = job
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val cJob = job ?: return
        binding.apply {
            jobTitle.text = cJob.serviceName
            jobClientName.text = cJob.clientName
            jobDescription.text = cJob.description
            jobRealizationTime.text = cJob.realizationTime
            jobCreationTime.text = "4 godziny temu" // TODO
        }
    }

}
