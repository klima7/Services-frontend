package com.klima7.services.client.features.newjob.location

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementLastLocationBinding
import com.klima7.services.common.models.LastLocation
import com.xwray.groupie.databinding.BindableItem


class LastLocationItem(
    private val location: LastLocation,
    private val listener: Listener
) : BindableItem<ElementLastLocationBinding>() {

    override fun bind(binding: ElementLastLocationBinding, position: Int) {
        binding.name = location.placeName
        binding.elemlastlocationContainer.setOnClickListener {
            listener.onLastLocationClicked(location)
        }
    }

    override fun getLayout() = R.layout.element_last_location

    interface Listener {
        fun onLastLocationClicked(location: LastLocation)
    }

}
