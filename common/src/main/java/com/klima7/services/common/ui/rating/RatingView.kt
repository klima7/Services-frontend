package com.klima7.services.common.ui.rating

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewRatingBinding
import com.klima7.services.common.domain.models.Rating
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil


class RatingView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var rating: Rating? = null
    private var binding: ViewRatingBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_rating, this, true);
        refreshView()
    }

    fun setRating(rating: Rating?) {
        this.rating = rating
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val cRating = rating ?: return

        binding.ratingComment.text = cRating.comment
        binding.ratingServiceName.text = cRating.serviceName
        binding.ratingRating.rating = cRating.rating.toFloat()
    }
}
