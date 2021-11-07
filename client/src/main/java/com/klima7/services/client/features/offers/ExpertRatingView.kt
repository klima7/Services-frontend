package com.klima7.services.client.features.offers

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.klima7.services.client.R
import com.klima7.services.client.databinding.ViewExpertRatingBinding
import com.klima7.services.common.models.Expert

@BindingAdapter( "expertrating_expert")
fun setExpert(expertRatingView: ExpertRatingView, expert: Expert?) {
    if(expert != null) {
        expertRatingView.setExpert(expert)
    }
}

class ExpertRatingView : FrameLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        refreshView()
    }

    private var binding: ViewExpertRatingBinding
    private var expert: Expert? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_expert_rating, this, true)
        refreshView()
    }

    fun setExpert(expert: Expert?) {
        this.expert = expert
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val cExpert = expert ?: return
        binding.expert = cExpert
        binding.expertratingviewRating.rating = cExpert.rating.toFloat()
    }

}
