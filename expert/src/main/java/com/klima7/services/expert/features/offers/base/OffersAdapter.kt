package com.klima7.services.expert.features.offers.base

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.klima7.services.common.models.Offer

class OffersAdapter(
    private val onOfferListener: OnOfferListener
): PagingDataAdapter<Offer, OfferViewHolder>(CommentsComparator) {

    object CommentsComparator: DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(parent, onOfferListener)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getOffer(position: Int): Offer? {
        return getItem(position)
    }

    interface OnOfferListener {
        fun onOfferClicked(offer: Offer)
        fun onShowJobClicked(offer: Offer)
    }

}

