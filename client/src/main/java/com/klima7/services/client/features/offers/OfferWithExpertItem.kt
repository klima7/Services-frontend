package com.klima7.services.client.features.offers

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementOfferBinding
import com.klima7.services.common.components.views.AvatarView
import com.klima7.services.common.extensions.uppercaseFirst
import com.klima7.services.common.models.OfferWithExpert
import com.klima7.services.common.models.Role
import com.klima7.services.common.ui.OfferStatusDescription
import com.xwray.groupie.databinding.BindableItem


class OfferWithExpertItem(
    private val offerWithExpert: OfferWithExpert,
    private val listener: Listener,
) : BindableItem<ElementOfferBinding>() {

    override fun bind(binding: ElementOfferBinding, position: Int) {
        val context = binding.offerCard.context

        binding.offerWithExpert = offerWithExpert

        val statusName = OfferStatusDescription.get(offerWithExpert.offer.status).getText(context)
        binding.status = statusName.uppercaseFirst()

        binding.offerelemExpertCard.setOnClickListener {
            listener.offerExpertClicked(offerWithExpert, binding.offerAvatar)
        }

        binding.offerelemContentCard.setOnClickListener {
            listener.offerContentClicked(offerWithExpert)
        }

        binding.offerRating.rating = offerWithExpert.expert.rating.toFloat()

        binding.offerLastMessage.setRole(Role.CLIENT)
        binding.offerLastMessage.setMessage(offerWithExpert.offer.lastMessage)
        binding.offerLastMessage.setReadTime(offerWithExpert.offer.clientReadTime)

    }

    override fun getLayout() = R.layout.element_offer

    interface Listener {
        fun offerExpertClicked(offerWithExpert: OfferWithExpert, avatarView: AvatarView)
        fun offerContentClicked(offerWithExpert: OfferWithExpert)
    }

}