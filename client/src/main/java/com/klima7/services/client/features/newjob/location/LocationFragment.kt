package com.klima7.services.client.features.newjob.location

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
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
        configureAutocomplete()
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

//        groupieAdapter += ProgressItem(2, "Wybierz lokalizację")
    }

    private fun configureAutocomplete() {
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.location_autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setCountries("pl")

    }

}