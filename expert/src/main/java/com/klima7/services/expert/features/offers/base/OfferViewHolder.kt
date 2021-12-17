package com.klima7.services.expert.features.offers.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.extensions.uppercaseFirst
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.Role
import com.klima7.services.common.ui.OfferStatusDescription
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ElementOfferBinding

class OfferViewHolder private constructor(
    private val binding: ElementOfferBinding,
    private val context: Context,
    private val onOfferListener: OffersAdapter.OnOfferListener
    ): RecyclerView.ViewHolder(binding.root) {


    companion object {
        operator fun invoke(parent: ViewGroup, onOfferListener: OffersAdapter.OnOfferListener): OfferViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ElementOfferBinding = DataBindingUtil.inflate(inflater,
                R.layout.element_offer, parent, false)
            return OfferViewHolder(binding, parent.context, onOfferListener)
        }
    }

    fun bind(offer: Offer?) {
        if(offer == null)
            return
        binding.offer = offer
        binding.offerCard.setOnClickListener {
            onOfferListener.onOfferClicked(offer)
        }
        binding.offerelemShowJobButton.setOnClickListener {
            onOfferListener.onShowJobClicked(offer)
        }

        val statusName = OfferStatusDescription.get(offer.status).getText(context)
        binding.status = statusName.uppercaseFirst()

        binding.offerLastMessage.setRole(Role.EXPERT)
        binding.offerLastMessage.setMessage(offer.lastMessage)
        binding.offerLastMessage.setReadTime(offer.expertReadTime)
    }

}