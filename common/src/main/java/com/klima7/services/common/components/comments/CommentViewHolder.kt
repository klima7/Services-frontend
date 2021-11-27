package com.klima7.services.common.components.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementCommentBinding
import com.klima7.services.common.models.Rating

class CommentViewHolder private constructor(
    private val binding: ElementCommentBinding,
    private val listener: CommentsAdapter.OnCommentListener,
):
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        operator fun invoke(parent: ViewGroup, listener: CommentsAdapter.OnCommentListener): CommentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ElementCommentBinding = DataBindingUtil.inflate(inflater,
                R.layout.element_comment, parent, false)
            return CommentViewHolder(binding, listener)
        }
    }

    fun bind(rating: Rating?) {
        if(rating == null)
            return
        binding.commentRatingView.setRating(rating)
        binding.elemcommentContainer.setOnClickListener {
            listener.onCommentClicked(rating)
        }
    }

}