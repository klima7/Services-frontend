package com.klima7.services.common.ui.profile.area

import android.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileAreaBinding
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProfileAreaFragment: BaseFragment<FragmentProfileAreaBinding>(), OnMapReadyCallback {

    override val layoutId = R.layout.fragment_profile_area
    override val viewModel = BaseViewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().getViewModel<ProfileViewModel>()
    }

    private lateinit var map: GoogleMap
    private lateinit var circle: Circle

    override fun init() {
        super.init()

        val mapFragment = childFragmentManager.findFragmentById(R.id.profile_area_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->

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

        viewModel.placeCoords.observe(viewLifecycleOwner) { center ->
            circle.center = center
        }

        viewModel.circleVisible.observe(viewLifecycleOwner) { visible ->
            circle.isVisible = visible
        }

        viewModel.radius.observe(viewLifecycleOwner) { radius ->
            circle.radius = radius.toDouble() * 1000
        }

        viewModel.mapBounds.observe(viewLifecycleOwner) { bounds ->
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20))
        }
    }
}