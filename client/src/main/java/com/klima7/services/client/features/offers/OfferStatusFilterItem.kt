package com.klima7.services.client.features.offers

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementOfferStatusFilterBinding
import com.klima7.services.common.components.views.StatusSelectionView
import com.klima7.services.common.models.OfferStatus
import com.xwray.groupie.databinding.BindableItem


class OfferStatusFilterItem(
    private val listener: StatusSelectionView.Listener,
): BindableItem<ElementOfferStatusFilterBinding>() {

    private var binding: ElementOfferStatusFilterBinding? = null
    private var selected: Set<OfferStatus>? = null

    override fun bind(binding: ElementOfferStatusFilterBinding, position: Int) {
        this.binding = binding
        val cSelected = selected
        if(cSelected != null) {
            binding.elemofferstatusfilterSelection.setSelected(cSelected)
        }
        binding.elemofferstatusfilterSelection.setListener(listener)
    }

    override fun getLayout() = R.layout.element_offer_status_filter

    fun setSelectedStatuses(selected: Set<OfferStatus>) {
        this.selected = selected
        binding?.elemofferstatusfilterSelection?.setSelected(selected)
    }
}