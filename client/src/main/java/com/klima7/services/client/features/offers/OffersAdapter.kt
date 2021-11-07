package com.klima7.services.client.features.offers

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.models.OfferWithExpert

class OffersAdapter(
    private val onOfferListener: OnOfferListener,
    private var offers: List<OfferWithExpert> = listOf()
): RecyclerView.Adapter<OfferViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setOffers(offers: List<OfferWithExpert>) {
        this.offers = offers
        Log.i("Hello", "Setting offers to: $offers")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(parent, onOfferListener)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    override fun getItemCount(): Int {
        Log.i("Hello", "Size: ${this.offers.size}")
        return this.offers.size
    }

    interface OnOfferListener {
        fun onOfferClicked(offerWithExpert: OfferWithExpert);
    }

}

