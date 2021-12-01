package com.klima7.services.expert.features.services

import com.klima7.services.common.models.Category
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ElementExpandableCategoryBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.databinding.BindableItem

class ExpandableCategoryItem(
    val category: Category,
    val listener: ExpandableCategoryItem.Listener
): BindableItem<ElementExpandableCategoryBinding>() , ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(binding: ElementExpandableCategoryBinding, position: Int) {
        binding.elemexpandablecategoryContainer.setOnClickListener {
            expandableGroup.onToggleExpanded()
            if(expandableGroup.isExpanded) {
                listener.categoryExpanded(category, expandableGroup)
            }
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    override fun getLayout() = R.layout.element_expandable_category

    private fun changeStuff(binding: ElementExpandableCategoryBinding) {

    }

    interface Listener {
        fun categoryExpanded(category: Category, expandableGroup: ExpandableGroup)
    }

}