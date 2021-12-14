package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.klima7.services.common.databinding.ViewServicesSelectionSpinnerBinding
import com.klima7.services.common.models.Service
import com.klima7.services.common.R

@BindingAdapter("servicesSelectionSpinner_services")
fun setServices(view: ServicesSelectionSpinner, newServices: List<Service>?) {
    val currentServices = view.getServices()
    if(newServices != null && newServices != currentServices) {
        view.setServices(newServices)
    }
}

@BindingAdapter("servicesSelectionSpinner_selectedIds")
fun setSelectedServicesIds(view: ServicesSelectionSpinner, newSelectedServicesIds: Set<String>?) {
    val currentSelectedServicesIds = view.getSelectedServicesIds()
    if(newSelectedServicesIds != null && currentSelectedServicesIds != newSelectedServicesIds) {
        view.setSelectedServicesIds(newSelectedServicesIds)
    }
}

@InverseBindingAdapter(attribute = "servicesSelectionSpinner_selectedIds")
fun getSelectedServicesIds(view: ServicesSelectionSpinner) = view.getSelectedServicesIds()

@BindingAdapter( "servicesSelectionSpinner_selectedIdsAttrChanged")
fun setSelectedServicesIdsListener(view: ServicesSelectionSpinner, attrChange: InverseBindingListener) {
    view.setListener(object: ServicesSelectionSpinner.Listener {
        override fun servicesSelected(view: ServicesSelectionSpinner, selectedServicesIds: Set<String>) {
            attrChange.onChange()
        }
    })
}

class ServicesSelectionSpinner(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewServicesSelectionSpinnerBinding

    private var services = emptyList<Service>()
    private var selectedServicesIds = emptySet<String>()
    private var listener: Listener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_services_selection_spinner, this, true)
        refreshView()
    }

    fun setServices(services: List<Service>) {
        this.services = services
        refreshView()
    }

    fun getServices(): List<Service> {
        return this.services
    }

    fun setSelectedServicesIds(selectedServicesIds: Set<String>) {
        this.selectedServicesIds = selectedServicesIds
        listener?.servicesSelected(this, selectedServicesIds)
        refreshView()
    }

    fun getSelectedServicesIds(): Set<String> {
        return selectedServicesIds
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    private fun refreshView() {
        binding.viewjobfilterClear.setOnClickListener { clear() }

        val spinner = binding.viewjobfilterSpinner
        spinner.setSearchHint("Napisz aby wyszukać")
        spinner.isSearchEnabled = true
        refreshCross()

        val sortedServices = services.sortedBy { service -> service.name }
        val items = prepareItems(sortedServices, selectedServicesIds)

        spinner.setItems(items) { items ->
            val selectedServicesIds = items.map { item ->
                val service = item.`object` as Service
                service.id
            }.toSet()
            this.selectedServicesIds = selectedServicesIds
            listener?.servicesSelected(this, selectedServicesIds)
            refreshCross()
        }
    }

    private fun refreshCross() {
        binding.clearVisible = selectedServicesIds.isNotEmpty()
    }

    private fun prepareItems(services: List<Service>, selectedIds: Set<String>): List<KeyPairBoolData> {
        return services.map { service ->
            val selected = selectedIds.contains(service.id)
            KeyPairBoolData(service.name, selected).apply {
                `object` = service
            }
        }
    }

    private fun clear() {
        this.setSelectedServicesIds(emptySet())
    }

    interface Listener {
        fun servicesSelected(view: ServicesSelectionSpinner, selectedServicesIds: Set<String>)
    }
}
