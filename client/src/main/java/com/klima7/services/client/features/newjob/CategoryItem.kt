package com.klima7.services.client.features.newjob

import com.klima7.services.client.R
import com.klima7.services.client.databinding.ElementCategoryBinding
import com.klima7.services.common.models.Category
import com.xwray.groupie.databinding.BindableItem


class CategoryItem(
    private val category: Category,
    private val listener: Listener
) : BindableItem<ElementCategoryBinding>() {

    override fun bind(binding: ElementCategoryBinding, position: Int) {
        binding.name = category.name
        binding.elemcategoryContainer.setOnClickListener {
            listener.onCategoryClicked(category)
        }
    }

    override fun getLayout() = R.layout.element_category

    interface Listener {
        fun onCategoryClicked(category: Category)
    }

}