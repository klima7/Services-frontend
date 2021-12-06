package com.klima7.services.client.features.offers

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementJobActiveBinding
import com.klima7.services.common.MAX_EXPERTS_PER_OFFER
import com.xwray.groupie.databinding.BindableItem
import java.text.SimpleDateFormat
import java.util.*


class JobActiveItem(
    private val listener: Listener,
    private val finishDate: Date?,
    private var offersCount: Int
): BindableItem<ElementJobActiveBinding>() {

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("dd.MM.yyy", Locale.US)
    }

    override fun bind(binding: ElementJobActiveBinding, position: Int) {
        binding.finishDate = if(finishDate != null) DATE_FORMAT.format(finishDate) else ""
        binding.elemjobactiveFinish.setOnClickListener {
            listener.finishJobClicked()
        }
        binding.maxCount = MAX_EXPERTS_PER_OFFER
        binding.count = offersCount
    }

    override fun getLayout() = R.layout.element_job_active

    interface Listener {
        fun finishJobClicked()
    }
}