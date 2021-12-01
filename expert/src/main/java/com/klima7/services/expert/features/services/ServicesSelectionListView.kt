package com.klima7.services.expert.features.services

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.models.CategoryWithServices
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ViewServicesSelectionListBinding
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign

@BindingAdapter("servicesSelectionList_all")
fun setAll(view: ServicesSelectionListView, all: Set<CategoryWithServices>?) {
    val allFixed = all ?: HashSet()
    view.setAll(allFixed)
}

@BindingAdapter("servicesSelectionList_selected")
fun setSelected(view: ServicesSelectionListView, selected: Set<String>?) {
    val selectedFixed = selected ?: HashSet()
    view.setSelected(selectedFixed)
}

@InverseBindingAdapter(attribute = "servicesSelectionList_selected")
fun getRatingValue(view: ServicesSelectionListView) = view.getSelected()

@BindingAdapter( "servicesSelectionList_selectedAttrChanged")
fun setSelectionListener(view: ServicesSelectionListView, attrChange: InverseBindingListener) {
    view.setSelectionListener(object: ServicesSelectionListView.SelectionListener {
        override fun onSelectionChanged(selected: Set<String>) {
            attrChange.onChange()
        }
    })
}

class ServicesSelectionListView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewServicesSelectionListBinding
    private var all: Set<CategoryWithServices> = HashSet()
    private var selected: Set<String> = HashSet()
    private var selectionListener: SelectionListener? = null

    private val groupieAdapter = GroupieAdapter()

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_services_selection_list, this, true)
        initialize()
        refreshView()
    }

    private fun initialize() {
        val context = binding.viewserviceselectionRecycler.context
        binding.viewserviceselectionRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupieAdapter
        }
    }

    fun setAll(all: Set<CategoryWithServices>) {
        this.all = all
        refreshView()
    }

    fun setSelected(selected: Set<String>) {
        this.selected = selected
        refreshView()
    }

    fun getSelected(): Set<String>? {
        return null
    }

    fun setSelectionListener(selectionListener: SelectionListener?) {
        this.selectionListener = selectionListener
    }

    private fun refreshView() {
        groupieAdapter.clear()
        addCategories()
    }

    private fun addCategories() {
        val sortedCategoriesWithServices = sortCategories(all)
        sortedCategoriesWithServices.forEach(this::addCategory)
    }

    private fun sortCategories(all: Set<CategoryWithServices>): List<CategoryWithServices> {
        val list = all.toMutableList()
        list.sortedBy { it.category.name }
        return list
    }

    private fun addCategory(categoryWithServices: CategoryWithServices) {
        groupieAdapter += ExpandableCategoryItem(categoryWithServices.category)
    }

    interface SelectionListener {
        fun onSelectionChanged(selected: Set<String>)
    }

}
