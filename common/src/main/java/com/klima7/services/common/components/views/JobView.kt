package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider
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

@BindingAdapter( "job_preferred")
fun setPreferred(jobView: JobView, preferred: Boolean?) {
    jobView.setPreferred(preferred ?: false)
}

class JobView : FrameLayout {

    companion object {
        private val format = SimpleDateFormat("dd.MM.yyy HH:mm", Locale.US)
        private const val shortDescriptionLines = 3
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private var binding: ViewJobBinding
    private var job: Job? = null
    private var short = false
    private var hideClient = false
    private var preferred = false

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_job, this, true)
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.Job, 0, 0)
        short = ta.getBoolean(R.styleable.Job_job_short, short)
        hideClient = ta.getBoolean(R.styleable.Job_job_hide_client, hideClient)
        ta.recycle()
    }

    fun setJob(job: Job?) {
        this.job = job
        refreshView()
        invalidate()
        requestLayout()
    }

    fun setPreferred(preferred: Boolean) {
        this.preferred = preferred
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val cJob = job

        binding.job = cJob
        binding.shorter = short
        binding.preferred = preferred
        binding.hideClient = hideClient
        binding.jobCreationTime.text = if(cJob != null) format.format(cJob.creationDate) else ""
        binding.jobviewDescription.maxLines = if(short) shortDescriptionLines else Int.MAX_VALUE
    }

}
