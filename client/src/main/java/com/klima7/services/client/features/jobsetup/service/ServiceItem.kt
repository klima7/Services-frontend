package com.klima7.services.client.features.jobsetup.service

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementServiceBinding
import com.klima7.services.common.models.Service
import com.xwray.groupie.databinding.BindableItem


class ServiceItem(
    private val service: Service,
    private val listener: Listener
) : BindableItem<ElementServiceBinding>() {

    override fun bind(binding: ElementServiceBinding, position: Int) {
        binding.name = service.name
        binding.elemserviceContainer.setOnClickListener {
            listener.onServiceClicked(service)
        }
    }

    override fun getLayout() = R.layout.element_service

    interface Listener {
        fun onServiceClicked(service: Service)
    }

}
