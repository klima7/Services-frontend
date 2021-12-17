package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
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
        BindingMethod(
            type = JobView::class,
            attribute = "job_show_active",
            method = "setShowActive"
        ),
    ]
)
class JobViewBindingMethods

class JobView : FrameLayout {

    companion object {
        private val FORMAT = SimpleDateFormat("dd.MM.yyy HH:mm", Locale.US)
        private const val SHORT_DESCRIPTION_LINES = 3
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private var binding: ViewJobBinding
    private var job: Job? = null
    private var showActive = false
    private var clickListener: (() -> Unit)? = null
    private var short = false
    private var hideClient = false
    private var unreadChanges = 0

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

    fun setShowActive(showActive: Boolean) {
        this.showActive = showActive
        refreshView()
        invalidate()
        requestLayout()
    }

    fun setUnreadChanges(unreadChanges: Int) {
        this.unreadChanges = unreadChanges
        refreshView()
    }

    fun setClickListener(clickListener: (() -> Unit)?) {
        this.clickListener = clickListener
    }

    private fun refreshView() {
        val cJob = job

        val pinRes = if(!showActive || showActive && job?.active == false) {
            R.drawable.pin_red
        }
        else {
            R.drawable.pin_green
        }

        binding.apply {
            job = cJob
            shorter = short
            jobCreationTime.text = if(cJob != null) FORMAT.format(cJob.creationDate) else ""
            jobviewDescription.maxLines = if(short) SHORT_DESCRIPTION_LINES else Int.MAX_VALUE
            if(clickListener != null) {
                jobCard.setOnClickListener { clickListener?.invoke() }
            }
            jobPin.setImageResource(pinRes)
        }
        binding.hideClient = hideClient
        binding.unreadChanges = unreadChanges
    }

}
