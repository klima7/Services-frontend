package com.klima7.services.client.features.newjob.location

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.LastLocation
import com.klima7.services.common.platform.BaseViewModel

class LocationViewModel(
    private val addLastLocationUC: AddLastLocationUC,
): BaseViewModel() {

    private lateinit var serviceId: String
    val serviceName = MutableLiveData<String>()

    val lastLocationsVisible = MutableLiveData(true)

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    fun start(categoryId: String, categoryName: String) {
        this.serviceId = categoryId
        this.serviceName.value = categoryName
    }

    fun refresh() {

    }

    fun locationSelected(placeId: String, placeName: String) {
        Log.i("Hello", "Location selected; name: $placeName; id: $placeId")
        addLastLocation(placeId, placeName)
    }

    private fun addLastLocation(placeId: String, placeName: String) {
        addLastLocationUC.start(
            viewModelScope,
            AddLastLocationUC.Params(placeId, placeName),
            { },
            {
                Log.i("Hello", "Add last location success!")
            }
        )
    }

}