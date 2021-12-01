package com.klima7.services.expert.features.services

import com.airbnb.lottie.LottieAnimationView
import com.klima7.services.common.models.Category
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ElementExpandableCategoryBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.databinding.BindableItem

class ExpandableCategoryItem(
    private val category: Category,
    private val listener: Listener
): BindableItem<ElementExpandableCategoryBinding>() , ExpandableItem {

    companion object {
        const val COLLAPSED_FRAME = 0
        const val EXPANDED_FRAME = 4
    }

    private lateinit var binding: ElementExpandableCategoryBinding
    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(binding: ElementExpandableCategoryBinding, position: Int) {
        this.binding = binding
        val expandIcon = binding.elemexpandablecategoryExpandIcon

        expandIcon.frame = if(expandableGroup.isExpanded) EXPANDED_FRAME else COLLAPSED_FRAME
        binding.name = category.name

        binding.elemexpandablecategoryContainer.setOnClickListener {
            toggleExpanded()
        }
    }

    fun toggleExpanded() {
        setExpanded(!expandableGroup.isExpanded)
    }

    fun setExpanded(expanded: Boolean) {
        if(expandableGroup.isExpanded == expanded) {
            return
        }

        animate(binding, expanded)
        expandableGroup.isExpanded = expanded

        if(expanded) {
            listener.categoryExpanded(this)
        }
    }

    private fun animate(binding: ElementExpandableCategoryBinding, newExpanded: Boolean) {
        val icon = binding.elemexpandablecategoryExpandIcon
        if(newExpanded) {
            animateIconExpand(icon)
        }
        else {
            animateIconCollapse(icon)
        }
    }

    private fun animateIconExpand(icon: LottieAnimationView) {
        icon.frame = COLLAPSED_FRAME
        icon.setMaxFrame(EXPANDED_FRAME)
        icon.speed = 1f
        icon.playAnimation()
    }

    private fun animateIconCollapse(icon: LottieAnimationView) {
        icon.frame = EXPANDED_FRAME
        icon.setMinFrame(COLLAPSED_FRAME)
        icon.speed = -1f
        icon.playAnimation()
    }

    override fun setExpandableGroup(expandableGroup: ExpandableGroup) {
        this.expandableGroup = expandableGroup
    }

    override fun getLayout() = R.layout.element_expandable_category

    interface Listener {
        fun categoryExpanded(expandableCategoryItem: ExpandableCategoryItem)
    }

}