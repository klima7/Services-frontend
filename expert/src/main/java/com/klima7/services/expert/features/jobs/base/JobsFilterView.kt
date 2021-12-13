package com.klima7.services.expert.features.jobs.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerListener
import com.klima7.services.common.models.CategoryWithServices
import com.klima7.services.common.models.Service
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.ViewJobFilterBinding
import com.klima7.services.expert.features.services.ServicesSelectionListView

@BindingAdapter("jobsFilterView_services")
fun setServices(view: JobsFilterView, newServices: List<Service>?) {
    if(newServices != null) {
        view.setServices(newServices)
    }
}

class JobsFilterView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewJobFilterBinding
    private var services = emptyList<Service>()

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_job_filter, this, true)
        refreshView()
    }

    fun setServices(services: List<Service>) {
        this.services = services
        refreshView()
    }

    private fun refreshView() {
        val spinner = binding.viewjobfilterSpinner

        spinner.setSearchHint("Napisz aby wyszukać")
        spinner.isSearchEnabled = false

        val items = listOf(
            KeyPairBoolData("Sadzenie drzew", false),
            KeyPairBoolData("Malowanie mebli", false),
            KeyPairBoolData("Spedycja", false),
        )

        spinner.setItems(items) { state ->
            Log.i("Hello", "Something changed: $state")
        }
    }
}
