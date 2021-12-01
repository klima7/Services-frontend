package com.klima7.services.expert.features.services

import android.widget.CheckBox
import com.klima7.services.common.models.Service
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ElementSelectableServiceBinding
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.GroupieViewHolder

class SelectableServiceItem(
    private val service: Service,
    private val selectionManager: SelectionManager,
): BindableItem<ElementSelectableServiceBinding>() {

    private var checkbox: CheckBox? = null

    override fun bind(binding: ElementSelectableServiceBinding, position: Int) {
        binding.name = service.name
        val checked = selectionManager.getServiceSelection(service)
        binding.elemselectableserviceCheckbox.isChecked = checked
        this.checkbox = binding.elemselectableserviceCheckbox
        binding.elemselectableserviceCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked != checked) {
                selectionManager.serviceSelectionChanged(service, isChecked)
            }
        }
    }

    override fun getLayout() = R.layout.element_selectable_service

    override fun unbind(viewHolder: GroupieViewHolder<ElementSelectableServiceBinding>) {
        checkbox?.setOnCheckedChangeListener(null)
        super.unbind(viewHolder)
    }

    interface SelectionManager {
        fun serviceSelectionChanged(service: Service, selected: Boolean)
        fun getServiceSelection(service: Service): Boolean
    }
}