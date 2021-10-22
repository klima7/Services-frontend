package com.klima7.services.common.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementCommentBinding
import com.klima7.services.common.domain.models.Rating

class CommentViewHolder private constructor(val binding: ElementCommentBinding):
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        operator fun invoke(parent: ViewGroup): CommentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ElementCommentBinding = DataBindingUtil.inflate(inflater,
                R.layout.element_comment, parent, false)
            return CommentViewHolder(binding)
        }
    }

    fun bind(ratingWithProfileImage: Rating?) {
        if(ratingWithProfileImage == null)
            return
        binding.commentRatingView.setRating(ratingWithProfileImage)
    }

}