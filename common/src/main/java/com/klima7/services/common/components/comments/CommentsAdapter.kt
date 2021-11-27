package com.klima7.services.common.components.comments

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.klima7.services.common.models.Rating

class CommentsAdapter(
    private val listener: OnCommentListener,
): PagingDataAdapter<Rating, CommentViewHolder>(CommentsComparator) {

    object CommentsComparator: DiffUtil.ItemCallback<Rating>() {
        override fun areItemsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnCommentListener {
        fun onCommentClicked(rating: Rating);
    }

}

