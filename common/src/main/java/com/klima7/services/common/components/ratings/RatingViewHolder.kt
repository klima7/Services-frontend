package com.klima7.services.common.components.ratings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementRatingBinding
import com.klima7.services.common.models.Rating

class RatingViewHolder private constructor(
    private val binding: ElementRatingBinding,
    private val listener: RatingsAdapter.OnRatingListener,
):
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        operator fun invoke(parent: ViewGroup, listener: RatingsAdapter.OnRatingListener): RatingViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ElementRatingBinding = DataBindingUtil.inflate(inflater,
                R.layout.element_rating, parent, false)
            return RatingViewHolder(binding, listener)
        }
    }

    fun bind(rating: Rating?) {
        if(rating == null)
            return

        binding.apply {
            elemratingRatingView.setRating(rating)
            elemratingContainer.setOnClickListener {
                listener.onRatingClicked(rating, elemratingRatingView)
            }
        }
    }

}