package com.klima7.services.expert.features.services

import com.klima7.services.common.models.Service
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ElementSelectableServiceBinding
import com.xwray.groupie.databinding.BindableItem

class SelectableServiceItem(
    private val service: Service
): BindableItem<ElementSelectableServiceBinding>() {

    override fun bind(binding: ElementSelectableServiceBinding, position: Int) {

    }

    override fun getLayout() = R.layout.element_selectable_service

}