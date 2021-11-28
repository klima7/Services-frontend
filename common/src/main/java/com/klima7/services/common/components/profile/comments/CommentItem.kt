package com.klima7.services.common.components.profile.comments

import com.klima7.services.common.R
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.databinding.ElementCommentBinding
import com.klima7.services.common.models.Rating
import com.xwray.groupie.databinding.BindableItem

class CommentItem(
    private val rating: Rating,
    private val listener: Listener,
) : BindableItem<ElementCommentBinding>() {

    override fun bind(binding: ElementCommentBinding, position: Int) {
        binding.apply {
            commentRatingView.setRating(rating)
            elemcommentContainer.setOnClickListener {
                listener.onCommentClick(rating, commentRatingView)
            }
        }
    }

    override fun getLayout() = R.layout.element_comment

    interface Listener {
        fun onCommentClick(rating: Rating, ratingView: RatingView)
    }
}