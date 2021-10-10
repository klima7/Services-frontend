package com.klima7.services.expert.features.services

import android.content.Context
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesCategoryBinding
import com.robertlevonyan.views.expandable.Expandable
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServicesCategoryFragment: BaseFragment<FragmentServicesCategoryBinding>(), Expandable.ExpandingListener {

    override val layoutId = R.layout.fragment_services_category
    override val viewModel: ServicesCategoryViewModel by viewModel()

    private lateinit var adapter: ServicesCategoryAdapter
    private var pendingServices: CategorizedServices? = null

    override fun init() {
        super.init()
        adapter = ServicesCategoryAdapter(requireContext())
        binding.servicesCategoryList.adapter = adapter
        binding.servicesCategoryExpandable.expandingListener = this
        viewModel.services.observe(viewLifecycleOwner, { services -> updateServices(services) })
        if(viewModel.expanded.value == true)
            binding.servicesCategoryExpandable.expand()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val constPendingServices = pendingServices
        if(constPendingServices != null)
            viewModel.setServices(constPendingServices)
    }

    private fun updateServices(services: List<SelectableService>) {
        adapter.setServices(services)
        if(viewModel.expanded.value == true) {
            val exp = binding.servicesCategoryExpandable
            exp.collapse()
            exp.expand()
        }
    }

    fun setServices(cServices: CategorizedServices) {
        if(activity == null)
            pendingServices = cServices
        else
            viewModel.setServices(cServices)
    }

    fun getSelectedServices(): List<Service> = viewModel.getSelectedServices()

    override fun onCollapsed() {
        viewModel.expanded.value = false
    }

    override fun onExpanded() {
        viewModel.expanded.value = true
    }
}