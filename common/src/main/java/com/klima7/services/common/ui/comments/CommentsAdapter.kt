package com.klima7.services.common.ui.comments

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.klima7.services.common.domain.models.Rating

class CommentsAdapter: PagingDataAdapter<RatingWithProfileImage, CommentViewHolder>(CommentsComparator) {

    object CommentsComparator: DiffUtil.ItemCallback<RatingWithProfileImage>() {
        override fun areItemsTheSame(oldItem: RatingWithProfileImage, newItem: RatingWithProfileImage): Boolean {
            return oldItem.rating.id == newItem.rating.id
        }

        override fun areContentsTheSame(oldItem: RatingWithProfileImage, newItem: RatingWithProfileImage): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

