package com.klima7.services.expert.features.location

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.klima7.services.common.lib.failurable.FailurableViewModel

class LocationContentViewModel: FailurableViewModel() {

    val locationCoordinates = MutableLiveData<LatLng>()
    val radius = MutableLiveData(1.0F)

    fun locationSelected(coordinates: LatLng) {
        locationCoordinates.value = coordinates
    }

    fun radiusChanged(newRadius: Float) {
        radius.value = newRadius
    }

    fun saveClicked() {

    }

    override fun refresh() {
    }

}