package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewRatingBinding
import com.klima7.services.common.models.Rating
import java.text.SimpleDateFormat
import java.util.*

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = RatingView::class,
            attribute = "rating_rating",
            method = "setRating"
        ),
    ]
)
class RatingViewBindingMethods

class RatingView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewRatingBinding

    private var rating: Rating? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_rating, this, true)
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
        binding.ratingClientName.text = cRating.clientName

        val format = SimpleDateFormat("dd.MM.yyy", Locale.US)
        binding.ratingDate.text = format.format(cRating.date)
    }
}
