package com.klima7.services.expert.features.location

import android.graphics.Color
import android.util.Log
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLngBounds
import kotlin.math.cos


class LocationContentFragment: FailurableFragment<FragmentLoginBinding>(), OnMapReadyCallback {

    override val layoutId = R.layout.fragment_location
    override val viewModel: LocationContentViewModel by viewModel()

    private lateinit var map: GoogleMap
    private lateinit var circle: Circle

    override fun onFirstCreation() {
        super.onFirstCreation()
        configureFragment()
    }

    override fun init() {
        super.init()

        val mapFragment = childFragmentManager.findFragmentById(R.id.location_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun configureFragment() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.location_autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setCountries("pl")

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val latLng = place.latLng
                if(latLng != null) {
                    viewModel.locationSelected(latLng)
                }
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isScrollGesturesEnabled = false
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isZoomGesturesEnabled = false
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isTiltGesturesEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isIndoorLevelPickerEnabled = false
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = false

        val circleOptions = CircleOptions().apply {
            center(LatLng(0.0, 0.0))
            radius(0.0)
            strokeColor(Color.BLACK)
            fillColor(0x30ff0000)
            strokeWidth(2F)
        }

        circle = map.addCircle(circleOptions)

        viewModel.locationCoordinates.observe(viewLifecycleOwner) {
            updateMap(viewModel.locationCoordinates.value, viewModel.radius.value?.toDouble())
        }

        viewModel.radius.observe(viewLifecycleOwner) {
            updateMap(viewModel.locationCoordinates.value, viewModel.radius.value?.toDouble())
        }
    }

    private fun updateMap(location: LatLng?, radius: Double?) {
        Log.i("Hello", "updateMap")

        if(location == null || radius == null)
            return

        val bounds = createLatLngBounds(location, radius)
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10))

        circle.radius = radius * 1000
        circle.center = location
    }

    private fun createLatLngBounds(center: LatLng, radius: Double): LatLngBounds {
        val southWest = addDistance(center, -radius, -radius)
        val northEasst = addDistance(center, radius, radius)
        return LatLngBounds(southWest, northEasst)
    }

    private fun addDistance(start: LatLng, dx: Double, dy: Double): LatLng {
        val rEarth = 6378.0
        val latitude = start.latitude
        val longitude = start.longitude

        val newLatitude  = latitude  + (dy / rEarth) * (180 / Math.PI)
        val newLongitude = longitude + (dx / rEarth) * (180 / Math.PI) / cos(latitude * Math.PI/180)
        return LatLng(newLatitude, newLongitude)
    }
}
