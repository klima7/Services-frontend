package com.klima7.services.client.features.jobsetup.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.LastLocation
import com.klima7.services.common.models.SimpleLocation
import com.klima7.services.common.platform.BaseViewModel

class LocationViewModel(
    private val getLastLocationsUC: GetLastLocationsUC,
    private val addLastLocationUC: AddLastLocationUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowJobDetailsScreen(val location: SimpleLocation): Event()
    }

    private lateinit var serviceId: String
    val serviceName = MutableLiveData<String>()

    val lastLocations = MutableLiveData<List<LastLocation>>()
    val lastLocationsVisible = lastLocations.map { it.isNotEmpty() }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    init {
        loadLastLocations()
    }

    fun start(categoryId: String, categoryName: String) {
        this.serviceId = categoryId
        this.serviceName.value = categoryName
    }

    fun refresh() {
        loadLastLocations()
    }

    fun locationSelected(location: SimpleLocation) {
        addLastLocation(location)
        sendEvent(Event.ShowJobDetailsScreen(location))
    }

    private fun loadLastLocations() {
        loadState.value = LoadAreaView.State.LOAD
        getLastLocationsUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { lastLocations ->
                loadState.value = LoadAreaView.State.MAIN
                this.lastLocations.value = lastLocations
            }
        )
    }

    private fun addLastLocation(location: SimpleLocation) {
        addLastLocationUC.start(
            viewModelScope,
            AddLastLocationUC.Params(location),
            { },
            { }
        )
    }

}