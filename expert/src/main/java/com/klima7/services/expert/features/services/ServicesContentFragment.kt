package com.klima7.services.expert.features.services

import android.util.Log
import com.klima7.services.common.domain.models.Category
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesBinding
import com.klima7.services.expert.features.services.category.ServicesCategoryFragment
import com.klima7.services.expert.features.services.multicategory.ServicesMultiCategoriesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServicesContentFragment: FailurableFragment<FragmentServicesBinding>() {

    override val layoutId = R.layout.fragment_services
    override val viewModel: ServicesContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.servicesStarted()
    }

    override fun init() {
        super.init()
        binding.servicesSaveButton.setOnClickListener { saveButtonClicked() }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ServicesContentViewModel.Event.SetServices -> setServices(event.services)
        }
    }

    private fun setServices(services: List<CategorizedServices>) {
        val fragment = childFragmentManager.findFragmentById(R.id.services_services) as ServicesMultiCategoriesFragment
        fragment.setServices(services)
    }

    private fun saveButtonClicked() {
        val fragment = childFragmentManager.findFragmentById(R.id.services_services) as ServicesMultiCategoriesFragment
        val selected = fragment.getSelectedServices()
        Log.i("Hello", "Selected services: $selected")
    }
}
