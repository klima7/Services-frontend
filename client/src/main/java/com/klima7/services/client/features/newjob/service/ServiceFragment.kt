package com.klima7.services.client.features.newjob.service

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentServiceBinding
import com.klima7.services.client.features.newjob.location.LocationActivity
import com.klima7.services.client.ui.ProgressItem
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.xwray.groupie.groupiex.plusAssign


class ServiceFragment: BaseFragment<FragmentServiceBinding>(), ServiceItem.Listener {

    override val layoutId = R.layout.fragment_service
    override val viewModel: ServiceViewModel by viewModel()

    private val groupieAdapter = GroupieAdapter()
    private val servicesSection = Section()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val categoryId = arguments?.getString("categoryId") ?: throw Error("categoryId argument not supplied")
        val categoryName = arguments?.getString("categoryName") ?: throw Error("categoryName argument not supplied")
        viewModel.start(categoryId, categoryName)
    }

    override fun init() {
        super.init()

        binding.serviceToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        binding.serviceRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        groupieAdapter += ProgressItem(1, "Wybierz usługę")
        groupieAdapter += servicesSection

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
        val intent = Intent(activity, LocationActivity::class.java)
        intent.putExtra("serviceId", service.id)
        intent.putExtra("serviceName", service.name)
        startActivity(intent)
    }
}