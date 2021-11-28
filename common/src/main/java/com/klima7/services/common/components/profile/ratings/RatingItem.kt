package com.klima7.services.common.components.profile.ratings

import com.klima7.services.common.R
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.databinding.ElementRatingBinding
import com.klima7.services.common.models.Rating
import com.xwray.groupie.databinding.BindableItem

class RatingItem(
    private val rating: Rating,
    private val listener: Listener,
) : BindableItem<ElementRatingBinding>() {

    override fun bind(binding: ElementRatingBinding, position: Int) {
        binding.apply {
            elemratingRatingView.setRating(rating)
            elemratingContainer.setOnClickListener {
                listener.onRatingClicked(rating, elemratingRatingView)
            }
        }
    }

    override fun getLayout() = R.layout.element_rating

    interface Listener {
        fun onRatingClicked(rating: Rating, ratingView: RatingView)
    }
}