package com.klima7.services.expert.features.services

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.CategoryWithServices
import com.klima7.services.common.models.Service
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ViewServicesSelectionListBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign

@BindingAdapter("servicesSelectionList_all")
fun setAll(view: ServicesSelectionListView, newAll: Set<CategoryWithServices>?) {
    val currentAll = view.getAll()
    if(currentAll != newAll) {
        val allFixed = newAll ?: HashSet()
        view.setAll(allFixed)
    }
}

@BindingAdapter("servicesSelectionList_selected")
fun setSelected(view: ServicesSelectionListView, newSelected: Set<String>?) {
    val alreadySelected = view.getSelected()
    if(alreadySelected != newSelected) {
        val selectedFixed = newSelected ?: HashSet()
        view.setSelected(selectedFixed)
    }
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

class ServicesSelectionListView(context: Context, attrs: AttributeSet?):
    FrameLayout(context, attrs), ExpandableCategoryItem.Listener, SelectableServiceItem.SelectionManager {

    private var binding: ViewServicesSelectionListBinding
    private var all: Set<CategoryWithServices> = HashSet()
    private var selected: Set<String> = HashSet()
    private var selectionListener: SelectionListener? = null

    private val groupieAdapter = GroupieAdapter()
    private val expandableCategories = mutableListOf<ExpandableCategoryItem>()

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
        val before = getSelected()
        this.all = all
        val after = getSelected()
        if(before != after) {
            selectionListener?.onSelectionChanged(after)
        }
        refreshView()
    }

    fun setSelected(selected: Set<String>) {
        val before = getSelected()
        this.selected = selected
        val after = getSelected()
        if(before != after) {
            selectionListener?.onSelectionChanged(after)
        }
        refreshView()
    }

    fun getAll(): Set<CategoryWithServices> {
        return all
    }

    fun getSelected(): Set<String> {
        return selected.filter { isInAll(it) }.toSet()
    }

    fun setSelectionListener(selectionListener: SelectionListener?) {
        this.selectionListener = selectionListener
    }

    private fun isInAll(serviceId: String): Boolean {
        return all.flatMap { it.services }.map { it.id }.contains(serviceId)
    }

    private fun refreshView() {
        groupieAdapter.clear()
        addCategories()
    }

    private fun addCategories() {
        val sortedCategoriesWithServices = sortCategories(all)
        expandableCategories.clear()
        sortedCategoriesWithServices.forEach(this::addCategory)
    }

    private fun sortCategories(all: Set<CategoryWithServices>): List<CategoryWithServices> {
        val list = all.toMutableList()
        list.sortedBy { it.category.name }
        return list
    }

    private fun addCategory(categoryWithServices: CategoryWithServices) {
        val categoryItem = ExpandableCategoryItem(categoryWithServices.category, this)
        val expandableGroup = ExpandableGroup(categoryItem)
        categoryWithServices.services.sortedBy { it.name }.forEach { service ->
            expandableGroup.add(SelectableServiceItem(service, this))
        }
        expandableCategories.add(categoryItem)
        groupieAdapter += expandableGroup
    }

    override fun categoryExpanded(expandableCategoryItem: ExpandableCategoryItem) {
        expandableCategories.forEach { group ->
            if(group != expandableCategoryItem) {
                group.setExpanded(false)
            }
        }
    }

    override fun serviceSelectionChanged(service: Service, selected: Boolean) {
        if(selected) {
            this.selected = this.selected.plus(service.id)
        }
        else {
            this.selected = this.selected.minus(service.id)
        }
        selectionListener?.onSelectionChanged(getSelected())
    }

    override fun getServiceSelection(service: Service): Boolean {
        return selected.contains(service.id)
    }

    interface SelectionListener {
        fun onSelectionChanged(selected: Set<String>)
    }

}
