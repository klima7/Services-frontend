package com.klima7.services.expert.features.area

import android.graphics.Color
import android.os.Bundle
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.faildialog.FailureDialogFragment
import com.klima7.services.common.ui.loadable.LoadableFragment
import com.klima7.services.common.ui.utils.showShortToast
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import com.klima7.services.expert.features.area.ClearableAutocompleteSupportFragment.OnClearListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class WorkingAreaContentFragment: LoadableFragment<FragmentLoginBinding>(), OnMapReadyCallback {

    companion object {
        const val SAVE_LOCATION_FAILURE_KEY = "SAVE_LOCATION_FAILURE_KEY"
    }

    override val layoutId = R.layout.fragment_location
    override val viewModel: WorkingAreaContentViewModel by viewModel()

    private lateinit var map: GoogleMap
    private lateinit var circle: Circle

    override fun onFirstCreation() {
        super.onFirstCreation()
        configureAutocomplete()
        viewModel.started()
    }

    override fun init() {
        super.init()
        val mapFragment = childFragmentManager.findFragmentById(R.id.location_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        childFragmentManager.setFragmentResultListener(SAVE_LOCATION_FAILURE_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retrySaveLocationClicked()
            }
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
                    viewModel.locationSelected(constId, constName, constCoords)
                }
            }

            override fun onError(status: Status) {
                // Place selection error
            }
        })

        autocompleteFragment.setOnPlaceClearedListener(object : OnClearListener {
            override fun onClear() = viewModel.locationCleared()
        })

        viewModel.placeName.observe(viewLifecycleOwner) { name ->
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

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is WorkingAreaContentViewModel.Event.ShowSaveLocationFailure -> showSaveLocationError(event.failure)
            is WorkingAreaContentViewModel.Event.Finish -> finish()
        }
    }

    private fun showSaveLocationError(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(SAVE_LOCATION_FAILURE_KEY,
            "Zmiana lokalizacji się nie powiodła.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

    private fun finish() {
        showShortToast("Lokalizacja zostałą zmieniona")
        requireActivity().finish()
    }
}
