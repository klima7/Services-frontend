package com.klima7.services.common.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.klima7.services.common.R
import com.klima7.services.common.domain.models.Rating

class CommentsAdapter: PagingDataAdapter<Rating, CommentViewHolder>(CommentsComparator) {

    object CommentsComparator: DiffUtil.ItemCallback<Rating>() {
        override fun areItemsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.id.text = getItem(position)?.id ?: "null"
    }

}

