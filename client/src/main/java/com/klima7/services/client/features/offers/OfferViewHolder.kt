package com.klima7.services.client.features.offers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementOfferBinding
import com.klima7.services.common.models.OfferWithExpert

class OfferViewHolder private constructor(
    private val binding: ElementOfferBinding,
    private val onOfferListener: OffersAdapter.OnOfferListener
): RecyclerView.ViewHolder(binding.root) {

    companion object {
        operator fun invoke(parent: ViewGroup, onOfferListener: OffersAdapter.OnOfferListener): OfferViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ElementOfferBinding = DataBindingUtil.inflate(inflater,
                R.layout.element_offer, parent, false)
            return OfferViewHolder(binding, onOfferListener)
        }
    }

    fun bind(offerWithExpert: OfferWithExpert?) {
        if(offerWithExpert == null)
            return
        binding.offerWithExpert = offerWithExpert
    }

}