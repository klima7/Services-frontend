package com.klima7.services.client.features.jobsetup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.anton46.stepsview.StepsView
import com.klima7.services.client.R
import com.klima7.services.client.databinding.ViewNewJobProgressBinding

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = NewJobProgressView::class,
            attribute = "njp_position",
            method = "setPosition"
        ),
    ]
)
class NewJobProgressBindingMethods

class NewJobProgressView : FrameLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private var binding: ViewNewJobProgressBinding
    private var position = 0

    private var category: String
    private var service: String
    private var location: String
    private var details: String

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_new_job_progress, this, true)
        category = context.getString(R.string.job_setup__stage_category)
        service = context.getString(R.string.job_setup__stage_service)
        location = context.getString(R.string.job_setup__stage_location)
        details = context.getString(R.string.job_setup__stage_details)
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.NewJobProgress, 0, 0)
        position = ta.getInt(R.styleable.NewJobProgress_njp_position, 0)
        ta.recycle()
    }

    fun setPosition(position: Int) {
        this.position = position
        refreshView()
    }

    private fun refreshView() {
        val holder = binding.viewprogressHolder
        val context = holder.context
        val stepsView = StepsView(context)

        stepsView
            .setLabels(listOf(category, service, location, details).toTypedArray())
            .setBarColorIndicator(context.resources.getColor(R.color.material_blue_grey_800, null))
            .setProgressColorIndicator(context.resources.getColor(R.color.quantum_googgreen500, null))
            .setLabelColorIndicator(context.resources.getColor(R.color.black, null))
            .setCompletedPosition(position)
            .drawView()

        holder.removeAllViews()
        holder.addView(stepsView)
    }

}
