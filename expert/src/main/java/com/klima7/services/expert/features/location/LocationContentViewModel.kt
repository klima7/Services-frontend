package com.klima7.services.expert.features.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.klima7.services.common.lib.failurable.FailurableViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos

class LocationContentViewModel: FailurableViewModel() {

    private val POLAND_BOUNDS = LatLngBounds(
        LatLng(49.0, 14.0),
        LatLng(55.0, 25.0),
    )

    init {
        viewModelScope.launch {
            delay(5000)
            currentScrollPosition.value = 10.0f
            maxScrollPosition.value = 10.0f
        }
    }

    private var locationId: String? = null

    val visibleName = MutableLiveData<String>("Warszawa")

    // Preview map
    val visibleCenterCoordinates = MutableLiveData<LatLng>()
    val visibleRadius = MutableLiveData<Int?>(null)
    val visibleBounds = MutableLiveData(POLAND_BOUNDS)

    // Slider
    val currentScrollPosition = MutableLiveData(1.0F)
    val maxScrollPosition = MutableLiveData(100.0F)
    val scrollEnabled = MutableLiveData(true)

    fun locationChanged(id: String, name: String, coords: LatLng) {
        locationId = id

        visibleCenterCoordinates.value = coords
        visibleName.value = name
        updateBounds()
    }

    fun radiusChanged(newRadius: Float) {
        visibleRadius.value = newRadius.toInt()
        updateBounds()
    }

    fun saveClicked() {

    }

    override fun refresh() {
    }

    private fun updateBounds() {
        val constCoordinates = visibleCenterCoordinates.value
        visibleBounds.value = if(constCoordinates == null) {
            POLAND_BOUNDS
        }
        else {
            createCircleBounds(constCoordinates, visibleRadius.value?.toDouble() ?: 0.0)
        }
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