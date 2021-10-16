package com.klima7.services.expert.features.services.multicategory

import android.content.Context
import android.os.Bundle
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesMultiCategoryBinding
import com.klima7.services.expert.features.services.CategorizedSelectableServices
import com.klima7.services.expert.features.services.category.ServicesCategoryFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServicesMultiCategoriesFragment: BaseFragment<FragmentServicesMultiCategoryBinding>() {

    override val layoutId = R.layout.fragment_services_multi_category
    override val viewModel: ServicesMultiCategoryViewModel by viewModel()

    private var fragmentsTags = mutableListOf<String>()
    private var pendingServices: List<CategorizedSelectableServices>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val constPendingServices = pendingServices
        if(constPendingServices != null)
            viewModel.setServices(constPendingServices)
    }

    fun setServices(services: List<CategorizedSelectableServices>) {
        if(activity == null)
            pendingServices = services
        else
            viewModel.setServices(services)
    }

    fun getSelectedServices(): List<Service> {
        val selectedServices = mutableListOf<Service>()
        fragmentsTags.forEach { tag ->
            val fragment = getFragment(tag)
            selectedServices.addAll(fragment?.getSelectedServices() ?: listOf())
        }
        return selectedServices
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ServicesMultiCategoryViewModel.Event.SetServices -> updateServices(event.services)
        }
    }

    private fun updateServices(categorizedSelectableServices: List<CategorizedSelectableServices>) {
        val container = binding.servicesMultiCategoryContainer

        // Removing old
        var transaction = childFragmentManager.beginTransaction()
        fragmentsTags.forEach { tag ->
            val fragment = getFragment(tag)
            if(fragment != null)
                transaction = transaction.remove(fragment)
        }

        // Adding new
        val newFragmentsTags = mutableListOf<String>()
        categorizedSelectableServices.forEach { categorizedService ->
            val fragment = ServicesCategoryFragment()
            fragment.setServices(categorizedService)
            val tag = categorizedService.category.id
            transaction = transaction.add(container.id, fragment, tag)
            newFragmentsTags.add(tag)
        }

        transaction.commit()
        fragmentsTags = newFragmentsTags
    }

    private fun getFragment(tag: String): ServicesCategoryFragment? {
        return childFragmentManager.findFragmentByTag(tag) as ServicesCategoryFragment?
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("fragmentsTags", fragmentsTags.toTypedArray())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null) {
            val array = savedInstanceState.getStringArray("fragmentsTags") ?: arrayOf()
            fragmentsTags = array.toMutableList()
        }
    }

}