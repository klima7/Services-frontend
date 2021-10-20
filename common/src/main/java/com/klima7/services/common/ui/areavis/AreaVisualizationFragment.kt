package com.klima7.services.common.ui.areavis

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentAreaVisualizationBinding
import com.klima7.services.common.domain.models.WorkingArea
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.faildialog.FailureDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AreaVisualizationFragment: BaseFragment<FragmentAreaVisualizationBinding>(),
    OnMapReadyCallback {

    override val layoutId = R.layout.fragment_area_visualization
    override val viewModel: AreaVisualizationViewModel by viewModel()

    private lateinit var map: GoogleMap
    private lateinit var circle: Circle

    override fun init() {
        super.init()
        val mapFragment = childFragmentManager.findFragmentById(R.id.areavis_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun setArea(area: WorkingArea?) {
        viewModel.setArea(area)
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
            if(center != null) {
                circle.center = center
            }
        }

        viewModel.circleVisible.observe(viewLifecycleOwner) { visible ->
            circle.isVisible = visible
        }

        viewModel.radius.observe(viewLifecycleOwner) { radius ->
            if(radius != null) {
                circle.radius = radius.toDouble() * 1000
            }
        }

        viewModel.mapBounds.observe(viewLifecycleOwner) { bounds ->
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20))
        }
    }

}