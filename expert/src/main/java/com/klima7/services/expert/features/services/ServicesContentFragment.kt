package com.klima7.services.expert.features.services

import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.loadable.LoadableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesBinding
import com.klima7.services.expert.features.services.multicategory.ServicesMultiCategoriesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServicesContentFragment: LoadableFragment<FragmentServicesBinding>() {

    override val layoutId = R.layout.fragment_services
    override val viewModel: ServicesContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
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

    private fun setServices(services: List<CategorizedSelectableServices>) {
        val fragment = childFragmentManager.findFragmentById(R.id.services_services) as ServicesMultiCategoriesFragment
        fragment.setServices(services)
    }

    private fun saveButtonClicked() {
        val fragment = childFragmentManager.findFragmentById(R.id.services_services) as ServicesMultiCategoriesFragment
        val selected = fragment.getSelectedServices()
        viewModel.servicesSelected(selected)
    }
}
