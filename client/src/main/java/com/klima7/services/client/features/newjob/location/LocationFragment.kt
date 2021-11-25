package com.klima7.services.client.features.newjob.location

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentLocationBinding
import com.klima7.services.client.features.newjob.newjob.NewJobViewModel
import com.klima7.services.common.models.LastLocation
import com.klima7.services.common.models.SimpleLocation
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationFragment: BaseFragment<FragmentLocationBinding>(), LastLocationItem.Listener {

    override val layoutId = R.layout.fragment_location
    override val viewModel: LocationViewModel by viewModel()

    private val parentViewModel: NewJobViewModel by lazy {
        requireParentFragment().requireParentFragment().getViewModel()
    }

    private val groupieAdapter = GroupieAdapter()

    override fun onFirstCreation() {
        super.onFirstCreation()
        configureAutocomplete()
    }

    override fun init() {
        super.init()

        binding.locationRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.lastLocations.observe(viewLifecycleOwner, this::updateLastLocations)
    }

    private fun configureAutocomplete() {
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.location_autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setCountries("pl")

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val cId = place.id
                val cName = place.name
                val cCoords = place.latLng
                if(cId != null && cName != null && cCoords != null) {
                    viewModel.locationSelected(SimpleLocation(cId, cName))
                }
            }

            override fun onError(status: Status) {}
        })
    }

    private fun updateLastLocations(locations: List<LastLocation>) {
        groupieAdapter.clear()
        locations.forEach { location ->
            groupieAdapter += LastLocationItem(location, this)
        }
    }

    override fun onLastLocationClicked(location: LastLocation) {
        viewModel.locationSelected(location.toSimpleLocation())
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is LocationViewModel.Event.ShowJobDetailsScreen -> showJobDetailsScreen(event.location)
        }
    }

    private fun showJobDetailsScreen(location: SimpleLocation) {
        parentViewModel.setLocation(location)
        parentViewModel.showDetailsScreen()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}