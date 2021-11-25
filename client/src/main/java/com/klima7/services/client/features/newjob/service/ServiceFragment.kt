package com.klima7.services.client.features.newjob.service

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentServiceBinding
import com.klima7.services.client.features.newjob.location.LocationActivity
import com.klima7.services.client.features.newjob.newjob.NewJobFragment
import com.klima7.services.client.features.newjob.newjob.NewJobViewModel
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServiceFragment: BaseFragment<FragmentServiceBinding>(), ServiceItem.Listener {

    override val layoutId = R.layout.fragment_service
    override val viewModel: ServiceViewModel by viewModel()

    private val parentViewModel: NewJobViewModel by lazy {
        requireParentFragment().requireParentFragment().getViewModel()
    }

    private val groupieAdapter = GroupieAdapter()
    private val servicesSection = Section()

    override fun init() {
        super.init()

        binding.serviceRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        groupieAdapter += servicesSection

        parentViewModel.category.observe(viewLifecycleOwner, viewModel::setCategory)

        viewModel.services.observe(viewLifecycleOwner, this::updateServices)
    }

    private fun updateServices(services: List<Service>) {
        servicesSection.clear()
        services.forEach { service ->
            servicesSection += ServiceItem(service, this)
        }
    }

    override fun onServiceClicked(service: Service) {
        Log.i("Hello", "Service clicked: $service")
        showLocationScreen(service)
    }

    private fun showLocationScreen(service: Service) {
        parentViewModel.setService(service)
        parentViewModel.showLocationScreen()
    }
}