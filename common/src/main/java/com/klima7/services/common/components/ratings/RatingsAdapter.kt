package com.klima7.services.common.components.ratings

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.models.Rating

class RatingsAdapter(
    private val listener: OnRatingListener,
): PagingDataAdapter<Rating, RatingViewHolder>(RatingsComparator) {

    object RatingsComparator: DiffUtil.ItemCallback<Rating>() {
        override fun areItemsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rating, newItem: Rating): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        return RatingViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnRatingListener {
        fun onRatingClicked(rating: Rating, ratingView: RatingView);
    }

}

