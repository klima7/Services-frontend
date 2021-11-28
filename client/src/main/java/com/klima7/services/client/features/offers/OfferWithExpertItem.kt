package com.klima7.services.client.features.offers

import android.view.View
import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementOfferBinding
import com.klima7.services.common.components.views.AvatarView
import com.klima7.services.common.extensions.uppercaseFirst
import com.klima7.services.common.models.OfferWithExpert
import com.klima7.services.common.ui.OfferStatusStringifier
import com.xwray.groupie.databinding.BindableItem


class OfferWithExpertItem(
    private val offerWithExpert: OfferWithExpert,
    private val listener: Listener,
) : BindableItem<ElementOfferBinding>() {

    override fun bind(binding: ElementOfferBinding, position: Int) {
        val context = binding.offerCard.context
        binding.offerWithExpert = offerWithExpert
        binding.status = OfferStatusStringifier.stringify(context, offerWithExpert.offer.status).uppercaseFirst()

        binding.offerelemExpertCard.setOnClickListener {
            listener.offerExpertClicked(offerWithExpert, binding.offerAvatar)
        }

        binding.offerelemContentCard.setOnClickListener {
            listener.offerContentClicked(offerWithExpert)
        }

        binding.offerRating.rating = offerWithExpert.expert.rating.toFloat()
    }

    override fun getLayout() = R.layout.element_offer

    interface Listener {
        fun offerExpertClicked(offerWithExpert: OfferWithExpert, avatarView: AvatarView)
        fun offerContentClicked(offerWithExpert: OfferWithExpert)
    }

}