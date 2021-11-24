package com.klima7.services.client.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.klima7.services.client.R
import com.klima7.services.client.databinding.ViewNewJobProgressBinding

class NewJobProgressView : FrameLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private var binding: ViewNewJobProgressBinding
    private var position = 0
    private var message: String? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_new_job_progress, this, true)
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.NewJobProgress, 0, 0)
        position = ta.getInt(R.styleable.NewJobProgress_njp_position, 0)
        message = ta.getString(R.styleable.NewJobProgress_njp_message)
        ta.recycle()
    }

    private fun refreshView() {
        binding.message = message
        binding.viewprogressStepview
            .setLabels(listOf("Kategoria", "Usługa", "Lokalizacja", "Opis").toTypedArray())
            .setBarColorIndicator(context.resources.getColor(R.color.material_blue_grey_800, null))
            .setProgressColorIndicator(context.resources.getColor(R.color.quantum_googgreen500, null))
            .setLabelColorIndicator(context.resources.getColor(R.color.black, null))
            .setCompletedPosition(position)
            .drawView();
    }

}
