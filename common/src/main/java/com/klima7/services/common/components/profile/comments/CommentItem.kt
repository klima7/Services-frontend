package com.klima7.services.common.components.profile.comments

import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementCommentBinding
import com.klima7.services.common.models.Rating
import com.xwray.groupie.databinding.BindableItem

class CommentItem(
    private val rating: Rating,
) : BindableItem<ElementCommentBinding>() {

    override fun bind(binding: ElementCommentBinding, position: Int) {
        binding.commentRatingView.setRating(rating)
    }

    override fun getLayout() = R.layout.element_comment

}