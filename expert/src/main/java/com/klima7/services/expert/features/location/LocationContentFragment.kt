package com.klima7.services.expert.features.location

import android.graphics.Color
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import com.klima7.services.expert.features.info.InfoContentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLngBounds
import kotlin.math.cos


class LocationContentFragment: FailurableFragment<FragmentLoginBinding>(), OnMapReadyCallback {

    override val layoutId = R.layout.fragment_location
    override val viewModel: InfoContentViewModel by viewModel()

    private lateinit var mMap: GoogleMap

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.infoStarted()
        configureFragment()

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
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
                val geocoder = Geocoder(requireContext())
                val pos = geocoder.getFromLocationName(place.name, 1)[0]
                val latLng = place.latLng!!

                val bounds = createLatLngBounds(latLng, 2.0)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10))

                val circleOptions = CircleOptions()
                circleOptions.center(latLng)
                circleOptions.radius(2000.0)
                circleOptions.strokeColor(Color.BLACK)
                circleOptions.fillColor(0x30ff0000);
                circleOptions.strokeWidth(2F);
                mMap.addCircle(circleOptions)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
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

        val newLati  = latitude  + (dy / rEarth) * (180 / Math.PI)
        val newLongitude = longitude + (dx / rEarth) * (180 / Math.PI) / cos(latitude * Math.PI/180)
        return LatLng(newLati, newLongitude)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.i("Hello", "Map ready")
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        mMap.uiSettings.isScrollGesturesEnabled = false
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isZoomGesturesEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.uiSettings.isTiltGesturesEnabled = false
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isIndoorLevelPickerEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.uiSettings.isMyLocationButtonEnabled = false
        mMap.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = false

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
