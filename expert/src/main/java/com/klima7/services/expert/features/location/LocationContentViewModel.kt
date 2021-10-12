package com.klima7.services.expert.features.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.klima7.services.common.lib.failurable.FailurableViewModel
import com.klima7.services.common.lib.utils.CombinedLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos

class LocationContentViewModel: FailurableViewModel() {

    // Constants
    private val POLAND_BOUNDS = LatLngBounds(
        LatLng(49.0, 14.0),
        LatLng(55.0, 25.0),
    )

    // Internal data
    private var locationId: String? = null

    // Other
    val visibleName = MutableLiveData<String>("Warszawa")

    // Slider
    val currentScrollPosition = MutableLiveData(1.0F)
    val maxScrollPosition = MutableLiveData(100.0F)
    val scrollEnabled = visibleName.map { it.isNotEmpty() }

    // Preview map
    val circleVisible = MutableLiveData(true)
    val visibleCenterCoordinates = MutableLiveData<LatLng>(LatLng(52.22, 21.0))
    val visibleRadius = currentScrollPosition.map { it.toInt() }
    val visibleBounds = CombinedLiveData(circleVisible, visibleCenterCoordinates, visibleRadius) { getBounds() }

    fun locationChanged(newLocation: ChangedLocation?) {
        if(newLocation != null) {
            visibleCenterCoordinates.value = newLocation.coords
            visibleName.value = newLocation.name
            circleVisible.value = true
            locationId = newLocation.id
        }
        else {
            circleVisible.value = false
            visibleName.value = ""
            locationId = null
        }
    }

    fun saveClicked() {

    }

    override fun refresh() {
    }

    private fun getBounds(): LatLngBounds {
        val visible = circleVisible.value ?: false
        if(!visible) return POLAND_BOUNDS
        val center = visibleCenterCoordinates.value ?: return POLAND_BOUNDS
        val radius = visibleRadius.value ?: 10
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