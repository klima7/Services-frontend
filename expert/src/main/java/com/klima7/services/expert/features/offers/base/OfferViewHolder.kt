package com.klima7.services.expert.features.offers.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.extensions.uppercaseFirst
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus
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
        binding.offerelemFrame.setOnClickListener {
            onOfferListener.onOfferClicked(offer)
        }
        binding.offerelemShowJobButton.setOnClickListener {
            onOfferListener.onShowJobClicked(offer)
        }
        binding.status = stringifyOfferStatus(offer.status).uppercaseFirst()
    }

    private fun stringifyOfferStatus(status: OfferStatus): String {
        val resourceId = when(status) {
            OfferStatus.NEW -> R.string.offer_status_new
            OfferStatus.REJECTED -> R.string.offer_status_rejected
            OfferStatus.IN_REALIZATION -> R.string.offer_status_in_realization
            OfferStatus.DONE -> R.string.offer_status_done
        }
        return context.resources.getString(resourceId)
    }

}