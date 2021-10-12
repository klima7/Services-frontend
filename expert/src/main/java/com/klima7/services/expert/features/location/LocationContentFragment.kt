package com.klima7.services.expert.features.location

import android.graphics.Color
import android.util.Log
import android.widget.Button
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.maps.model.CircleOptions


class LocationContentFragment: FailurableFragment<FragmentLoginBinding>(), OnMapReadyCallback {

    override val layoutId = R.layout.fragment_location
    override val viewModel: LocationContentViewModel by viewModel()

    private lateinit var map: GoogleMap
    private lateinit var circle: Circle

    override fun onFirstCreation() {
        super.onFirstCreation()
        configureAutocomplete()
    }

    override fun init() {
        super.init()
        val mapFragment = childFragmentManager.findFragmentById(R.id.location_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.currentScrollPosition.observe(viewLifecycleOwner) {
            Log.i("Hello", "Scroll position: $it")
        }
    }

    private fun configureAutocomplete() {
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.location_autocomplete_fragment)
                    as ClearableAutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setCountries("pl")

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val constId = place.id
                val constName = place.name
                val constCoords = place.latLng
                if(constId != null && constName != null && constCoords != null) {
                    val location = ChangedLocation(constId, constName, constCoords)
                    viewModel.locationChanged(location)
                }
            }

            override fun onError(status: Status) {
                Log.i("Hello", "An error occurred: $status")
            }
        })

        autocompleteFragment.setOnPlaceClearedListener(object : OnClearListener {
            override fun onClear() {
                Log.i("Hello", "Clear detected")
                viewModel.locationChanged(null)
            }
        })

        val button = Button(requireContext())
        button.setOnClickListener {  }

        viewModel.visibleName.observe(viewLifecycleOwner) { name ->
            autocompleteFragment.setText(name)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.apply {
            isScrollGesturesEnabled = false
            isRotateGesturesEnabled = false
            isZoomGesturesEnabled = false
            isZoomControlsEnabled = false
            isTiltGesturesEnabled = false
            isCompassEnabled = false
            isIndoorLevelPickerEnabled = false
            isMapToolbarEnabled = false
            isMyLocationButtonEnabled = false
            isScrollGesturesEnabledDuringRotateOrZoom = false
        }

        val circleOptions = CircleOptions().apply {
            center(LatLng(0.0, 0.0))
            radius(0.0)
            strokeColor(Color.BLACK)
            fillColor(0x30ff0000)
            strokeWidth(2F)
        }

        circle = map.addCircle(circleOptions)

        viewModel.visibleCenterCoordinates.observe(viewLifecycleOwner) {
            circle.center = it
        }

        viewModel.circleVisible.observe(viewLifecycleOwner) {
            circle.isVisible = it
        }

        viewModel.visibleRadius.observe(viewLifecycleOwner) { radius ->
            circle.radius = if(radius != null)
                radius.toDouble() * 1000
            else
                0.0
        }

        viewModel.visibleBounds.observe(viewLifecycleOwner) {
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(it, 10))
        }
    }


}
