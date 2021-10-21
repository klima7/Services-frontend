package com.klima7.services.common.ui.rating

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewRatingBinding
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class RatingView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewRatingBinding

    private var rwpi: RatingWithProfileImage? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_rating, this, true);
        refreshView()
    }

    fun setRatingWithProfileImage(rwpi: RatingWithProfileImage?) {
        this.rwpi = rwpi
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val cRwpi = rwpi ?: return
        val cRating = cRwpi.rating

        binding.ratingComment.text = cRating.comment
        binding.ratingServiceName.text = cRating.serviceName
        binding.ratingRating.rating = cRating.rating.toFloat()
        binding.ratingClientName.text = cRating.clientName

        val format = SimpleDateFormat("dd.MM.yyy", Locale.US)
        binding.ratingDate.text = format.format(cRating.date)
    }
}
