package com.klima7.services.client.ui

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementProgressBinding
import com.xwray.groupie.databinding.BindableItem


class ProgressItem(
    private val completedPosition: Int,
    private val text: String,
) : BindableItem<ElementProgressBinding>() {

    override fun bind(binding: ElementProgressBinding, position: Int) {
        val context = binding.progressStepview.context
        binding.progressStepview
            .setLabels(listOf("Kategoria", "Usługa", "Lokalizacja", "Opis").toTypedArray())
            .setBarColorIndicator(context.resources.getColor(R.color.material_blue_grey_800, null))
            .setProgressColorIndicator(context.resources.getColor(R.color.quantum_googgreen500, null))
            .setLabelColorIndicator(context.resources.getColor(R.color.black, null))
            .setCompletedPosition(completedPosition)
            .drawView();
        binding.progressText.text = text
    }

    override fun getLayout() = R.layout.element_progress

}