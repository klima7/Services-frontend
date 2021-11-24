package com.klima7.services.client.features.newjob.location

import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentLocationBinding
import com.klima7.services.client.databinding.FragmentServiceBinding
import com.klima7.services.client.ui.ProgressItem
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationFragment: BaseFragment<FragmentLocationBinding>() {

    override val layoutId = R.layout.fragment_location
    override val viewModel: LocationViewModel by viewModel()

    private val groupieAdapter = GroupieAdapter()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val serviceId = arguments?.getString("serviceId") ?: throw Error("serviceId argument not supplied")
        val serviceName = arguments?.getString("serviceName") ?: throw Error("serviceName argument not supplied")
        viewModel.start(serviceId, serviceName)
    }

    override fun init() {
        super.init()

        binding.serviceToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        binding.locationRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        groupieAdapter += ProgressItem(2, "Wybierz lokalizację")
    }

}