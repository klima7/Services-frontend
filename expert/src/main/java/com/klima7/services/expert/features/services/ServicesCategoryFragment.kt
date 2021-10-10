package com.klima7.services.expert.features.services

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

    override fun onCollapsed() {
        viewModel.expanded.value = false
    }

    override fun onExpanded() {
        viewModel.expanded.value = true
    }

    override fun init() {
        super.init()

        adapter = ServicesCategoryAdapter(requireContext())

        binding.servicesCategoryContent.adapter = adapter
        binding.servicesCategoryExpandable.expandingListener = this

        viewModel.services.observe(viewLifecycleOwner, { services ->
            adapter.setServices(services)
            if(viewModel.expanded.value == true) {
                val exp = binding.servicesCategoryExpandable
                exp.collapse()
                exp.expand()
            }
        })

        if(viewModel.expanded.value == true) {
            binding.servicesCategoryExpandable.expand()
        }

        binding.servicesCategoryExpandable.expandingListener
    }

    fun setServices(services: List<Service>) {
        viewModel.setServices(services)
    }

    fun setName(name: String) {
        viewModel.setName(name)
    }

    fun getSelectedServices(): List<Service> = viewModel.getSelectedServices()

}