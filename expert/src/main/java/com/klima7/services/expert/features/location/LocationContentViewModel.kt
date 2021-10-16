package com.klima7.services.expert.features.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.WorkingArea
import com.klima7.services.common.ui.converters.toLatLng
import com.klima7.services.common.ui.loadable.LoadableViewModel
import com.klima7.services.common.ui.utils.CombinedLiveData
import com.klima7.services.expert.usecases.GetCurrentExpertUC
import kotlinx.coroutines.launch
import kotlin.math.cos

class LocationContentViewModel(
    private val expertsRepository: ExpertsRepository,
    private val getCurrentExpertUC: GetCurrentExpertUC
): LoadableViewModel() {

    companion object {
        private val POLAND_BOUNDS = LatLngBounds(
            LatLng(49.0, 14.0),
            LatLng(55.0, 25.0),
        )
    }

    sealed class Event: BaseEvent() {
        data class ShowSaveLocationFailure(val failure: Failure): Event()
    }

    // Internal data
    private var placeId: String? = null

    // Input values
    val placeName = MutableLiveData<String>()
    val placeCoords = MutableLiveData<LatLng>()
    val radiusFloat = MutableLiveData(1F)

    // Derived values
    val radius = radiusFloat.map { it.toInt() }
    val scrollEnabled = placeName.map { it.isNotEmpty() }
    val circleVisible = placeName.map { it.isNotEmpty() }
    val saveEnabled = placeName.map { it.isNotEmpty() }
    val mapBounds = CombinedLiveData(circleVisible, placeCoords, radius) { getBounds() }

    fun started() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    fun locationSelected(newPlaceId: String, newPlaceName: String, newPlaceCoords: LatLng) {
        placeCoords.value = newPlaceCoords
        placeName.value = newPlaceName
        placeId = newPlaceId
    }

    fun locationCleared() {
        placeName.value = ""
        placeId = null
    }

    fun saveClicked() {
        saveWorkingArea()
    }

    fun retrySaveLocationClicked() {
        saveWorkingArea()
    }

    private fun loadContent() {
        showLoading()
        viewModelScope.launch {
            getCurrentExpertUC.execute().foldS({ failure ->
                showFailure(failure)
            }, { expert ->
                initWithWorkingArea(expert.area)
                showMain()
            })
        }
    }

    private fun initWithWorkingArea(wa: WorkingArea?) {
        if(wa != null) {
            placeName.value = wa.location.name
            radiusFloat.value = wa.radius.toFloat()
            placeCoords.value = wa.location.coords.toLatLng()
        }
        else {
            placeName.value = ""
        }
    }

    private fun saveWorkingArea() {
        showPending()
        val constPlaceId = placeId
        val constRadius = radius.value
        if(constPlaceId != null && constRadius != null) {
            viewModelScope.launch {
                expertsRepository.setWorkingArea(constPlaceId, constRadius).fold({ failure ->
                    showMain()
                    sendEvent(Event.ShowSaveLocationFailure(failure))
                }, {
                    showMain()
                })
            }
        }
        else {
            showMain()
        }
    }

    private fun getBounds(): LatLngBounds {
        val visible = circleVisible.value ?: false
        if(!visible) return POLAND_BOUNDS
        val center = placeCoords.value ?: return POLAND_BOUNDS
        val radius = radius.value ?: 10
        return createCircleBounds(center, radius.toDouble())
    }

    private fun createCircleBounds(center: LatLng, radius: Double): LatLngBounds {
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