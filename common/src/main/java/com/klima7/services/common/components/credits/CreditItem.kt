package com.klima7.services.common.components.credits

import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementCreditBinding
import com.xwray.groupie.databinding.BindableItem


class CreditItem(
    private val credit: Credit
) : BindableItem<ElementCreditBinding>() {

    override fun bind(binding: ElementCreditBinding, position: Int) {
        val context = binding.creditText.context
        binding.apply {
            creditImage.setImageResource(credit.imageRes)
            creditText.text = context.getText(credit.textRes)
        }
    }

    override fun getLayout() = R.layout.element_credit

}