package com.klima7.services.common.ui.areavis

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.klima7.services.common.domain.models.Coordinates
import com.klima7.services.common.domain.models.WorkingArea
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.converters.toLatLng
import com.klima7.services.common.ui.utils.CombinedLiveData
import kotlin.math.cos

class AreaVisualizationViewModel: BaseViewModel() {

    companion object {
        private val POLAND_BOUNDS = LatLngBounds(
            LatLng(49.0, 14.0),
            LatLng(55.0, 25.0),
        )
    }

    // Input
    val placeCoords = MutableLiveData<LatLng?>(null)
    val radius = MutableLiveData<Int?>(null)

    val circleVisible = CombinedLiveData(placeCoords, radius) { it.all { it != null } }
    val mapBounds = CombinedLiveData(circleVisible, placeCoords, radius) { getBounds() }

    fun setRadius(radius: Int?) {
        this.radius.value = radius
    }

    fun setCoords(coords: LatLng?) {
        this.placeCoords.value = coords
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
        val northEast = addDistance(center, radius, radius)
        return LatLngBounds(southWest, northEast)
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