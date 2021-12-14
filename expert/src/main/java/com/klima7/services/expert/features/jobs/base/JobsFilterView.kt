package com.klima7.services.expert.features.jobs.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.klima7.services.common.models.Service
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ViewJobFilterBinding
import com.willy.ratingbar.BaseRatingBar

@BindingAdapter("jobsFilterView_services")
fun setServices(view: JobsFilterView, newServices: List<Service>?) {
    val currentServices = view.getServices()
    if(newServices != null && newServices != currentServices) {
        view.setServices(newServices)
    }
}

@BindingAdapter("jobsFilterView_selectedServicesIds")
fun setSelectedServicesIds(view: JobsFilterView, newSelectedServicesIds: Set<String>?) {
    val currentSelectedServicesIds = view.getSelectedServicesIds()
    if(newSelectedServicesIds != null && currentSelectedServicesIds != newSelectedServicesIds) {
        view.setSelectedServicesIds(newSelectedServicesIds)
    }
}

@InverseBindingAdapter(attribute = "jobsFilterView_selectedServicesIds")
fun getSelectedServicesIds(view: JobsFilterView) = view.getSelectedServicesIds()

@BindingAdapter( "jobsFilterView_selectedServicesIdsAttrChanged")
fun setSelectedServicesIdsListener(view: JobsFilterView, attrChange: InverseBindingListener) {
    view.setListener(object: JobsFilterView.Listener {
        override fun servicesSelected(view: JobsFilterView, selectedServicesIds: Set<String>) {
            attrChange.onChange()
        }
    })
}

class JobsFilterView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewJobFilterBinding

    private var services = emptyList<Service>()
    private var selectedServicesIds = emptySet<String>()
    private var listener: Listener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_job_filter, this, true)
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
        refreshView()
    }

    fun getSelectedServicesIds(): Set<String> {
        return selectedServicesIds
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    private fun refreshView() {
        val spinner = binding.viewjobfilterSpinner

        spinner.setSearchHint("Napisz aby wyszukać")
        spinner.isSearchEnabled = true

        val sortedServices = services.sortedBy { service -> service.name }
        val items = prepareItems(sortedServices, selectedServicesIds)

        spinner.setItems(items) { items ->
            val selectedServicesIds = items.map { item ->
                val service = item.`object` as Service
                service.id
            }.toSet()
            this.selectedServicesIds = selectedServicesIds
            listener?.servicesSelected(this, selectedServicesIds)
            Log.i("Hello", "Selected services: $selectedServicesIds")
        }
    }

    private fun prepareItems(services: List<Service>, selectedIds: Set<String>): List<KeyPairBoolData> {
        return services.map { service ->
            val selected = selectedIds.contains(service.id)
            KeyPairBoolData(service.name, selected).apply {
                `object` = service
            }
        }
    }

    interface Listener {
        fun servicesSelected(view: JobsFilterView, selectedServicesIds: Set<String>)
    }
}
