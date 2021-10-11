package com.klima7.services.expert.features.services.multicategory

import android.content.Context
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesMultiCategoryBinding
import com.klima7.services.expert.features.services.CategorizedServices
import com.klima7.services.expert.features.services.category.ServicesCategoryFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServicesMultiCategoriesFragment: BaseFragment<FragmentServicesMultiCategoryBinding>() {

    override val layoutId = R.layout.fragment_services_multi_category
    override val viewModel: ServicesMultiCategoryViewModel by viewModel()

    private var categoriesFragments = mutableListOf<ServicesCategoryFragment>()
    private var pendingServices: List<CategorizedServices>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val constPendingServices = pendingServices
        if(constPendingServices != null)
            viewModel.setServices(constPendingServices)
    }

    fun setServices(services: List<CategorizedServices>) {
        if(activity == null)
            pendingServices = services
        else
            viewModel.setServices(services)
    }

    fun getSelectedServices(): List<Service> {
        return listOf()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ServicesMultiCategoryViewModel.Event.SetServices -> updateServices(event.services)
        }
    }

    private fun updateServices(categorizedServices: List<CategorizedServices>) {
        val container = binding.servicesMultiCategoryContainer

        // Removing old
        var transaction = childFragmentManager.beginTransaction()
        categoriesFragments.forEach { fragment ->
            transaction = transaction.remove(fragment)
        }

        // Adding new
        val newCategoriesFragments = mutableListOf<ServicesCategoryFragment>()
        categorizedServices.forEach { categorizedService ->
            val fragment = ServicesCategoryFragment()
            fragment.setServices(categorizedService)
            newCategoriesFragments.add(fragment)
            transaction = transaction.add(container.id, fragment, categorizedService.category.id)
        }

        transaction.commit()
        categoriesFragments = newCategoriesFragments
    }

}