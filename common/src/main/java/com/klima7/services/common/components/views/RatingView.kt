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

    companion object {
        private const val SHORT_DESCRIPTION_LINES = 3
        private val FORMAT = SimpleDateFormat("dd.MM.yyy", Locale.US)
    }

    private var binding: ViewRatingBinding
    private var rating: Rating? = null
    private var short = false

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_rating, this, true)
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.Rating, 0, 0)
        short = ta.getBoolean(R.styleable.Rating_rating_short, short)
        ta.recycle()
    }

    fun setRating(rating: Rating?) {
        this.rating = rating
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val cRating = rating ?: return

        binding.apply {
            rating = cRating
            ratingRating.rating = cRating.rating.toFloat()
            ratingComment.maxLines = if(short) SHORT_DESCRIPTION_LINES else Int.MAX_VALUE
            ratingDate.text = FORMAT.format(cRating.date)
        }
    }
}
