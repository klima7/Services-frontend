package com.klima7.services.client.features.offers

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementNoOffersBinding
import com.xwray.groupie.databinding.BindableItem


class NoOffersItem : BindableItem<ElementNoOffersBinding>() {

    override fun bind(binding: ElementNoOffersBinding, position: Int) { }

    override fun getLayout() = R.layout.element_no_offers


}