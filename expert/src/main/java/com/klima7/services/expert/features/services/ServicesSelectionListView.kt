package com.klima7.services.expert.features.services

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.*
import com.klima7.services.common.models.CategoryWithServices
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ViewServicesSelectionListBinding

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = ServicesSelectionListView::class,
            attribute = "servicesSelectionList_all",
            method = "setAll"
        ),
        BindingMethod(
            type = ServicesSelectionListView::class,
            attribute = "servicesSelectionList_selected",
            method = "setSelected"
        ),
    ]
)
class ServicesSelectionListViewBindingMethods

@InverseBindingAdapter(attribute = "servicesSelectionList_selected")
fun getRatingValue(view: ServicesSelectionListView) = view.getSelected()

@BindingAdapter( "servicesSelectionList_selectedAttrChanged")
fun setSelectionListener(view: ServicesSelectionListView, attrChange: InverseBindingListener) {
    view.setSelectionListener(object: ServicesSelectionListView.SelectionListener {
        override fun onSelectionChanged(selected: Set<String>?) {
            attrChange.onChange()
        }
    })
}

class ServicesSelectionListView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewServicesSelectionListBinding
    private var all: Set<CategoryWithServices>? = null
    private var selected: Set<String>? = null
    private var selectionListener: SelectionListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_services_selection_list, this, true)
        refreshView()
    }

    fun setAll(all: Set<CategoryWithServices>?) {
        this.all = all
        refreshView()
    }

    fun setSelected(selected: Set<String>?) {
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

    }

    interface SelectionListener {
        fun onSelectionChanged(selected: Set<String>?)
    }

}
