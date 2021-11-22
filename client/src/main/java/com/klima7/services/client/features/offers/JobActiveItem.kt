package com.klima7.services.client.features.offers

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementJobActiveBinding
import com.xwray.groupie.databinding.BindableItem


class JobActiveItem(
    private val listener: Listener
): BindableItem<ElementJobActiveBinding>() {

    override fun bind(binding: ElementJobActiveBinding, position: Int) {
        binding.elemjobactiveFinish.setOnClickListener {
            listener.finishJobClicked()
        }
    }

    override fun getLayout() = R.layout.element_job_active

    interface Listener {
        fun finishJobClicked()
    }
}