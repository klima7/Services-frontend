package com.klima7.services.common.components.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewJobBinding
import com.klima7.services.common.models.Job
import java.text.SimpleDateFormat
import java.util.*

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = JobView::class,
            attribute = "job_job",
            method = "setJob"
        ),
    ]
)
class JobViewBindingMethods

class JobView : FrameLayout {

    private val format = SimpleDateFormat("dd.MM.yyy HH:mm", Locale.US)

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private var binding: ViewJobBinding
    private var job: Job? = null
    private var short = false

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_job, this, true)
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.Job, 0, 0)
        short = ta.getBoolean(R.styleable.Job_job_short, short)
        ta.recycle()
    }

    fun setJob(job: Job?) {
        this.job = job
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val cJob = job

        binding.job = cJob
        binding.jobCreationTime.text = if(cJob != null) format.format(cJob.creationDate) else ""
        binding.jobviewDescription.maxLines = 2
    }

}
