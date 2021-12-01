package com.klima7.services.expert.features.area

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.WorkingArea
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.ui.toLatLng
import com.klima7.services.expert.usecases.GetCurrentExpertUC

class WorkingAreaViewModel(
    private val getCurrentExpertUC: GetCurrentExpertUC,
    private val setCurrentExpertWorkingAreaUC: SetCurrentExpertWorkingAreaUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowSaveLocationFailure(val failure: Failure): Event()
        object Finish: Event()
    }

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    // Internal data
    private var placeId: String? = null

    // Input values
    val placeName = MutableLiveData<String>()
    val placeCoords = MutableLiveData<LatLng?>()
    val radiusFloat = MutableLiveData(1F)

    // Derived values
    val radius = radiusFloat.map { it.toInt() }
    val scrollEnabled = placeName.map { it.isNotEmpty() }
    val saveEnabled = placeName.map { it.isNotEmpty() }

    fun started() {
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun locationSelected(newPlaceId: String, newPlaceName: String, newPlaceCoords: LatLng) {
        placeCoords.value = newPlaceCoords
        placeName.value = newPlaceName
        placeId = newPlaceId
    }

    fun locationCleared() {
        placeName.value = ""
        placeCoords.value = null
        placeId = null
    }

    fun saveClicked() {
        save()
    }

    fun retrySaveLocationClicked() {
        save()
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getCurrentExpertUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            }, { expert ->
                initWithWorkingArea(expert.area)
                loadState.value = LoadAreaView.State.MAIN
            })
    }

    private fun initWithWorkingArea(wa: WorkingArea?) {
        if(wa != null) {
            placeName.value = wa.location.name
            radiusFloat.value = wa.radius.toFloat()
            placeCoords.value = wa.location.coords.toLatLng()
            placeId = wa.location.id
        }
        else {
            placeName.value = ""
        }
    }

    private fun save() {
        loadState.value = LoadAreaView.State.PENDING
        val constPlaceId = placeId
        val constRadius = radius.value
        if(constPlaceId != null && constRadius != null) {
            setCurrentExpertWorkingAreaUC.start(
                viewModelScope,
                SetCurrentExpertWorkingAreaUC.Params(constPlaceId, constRadius),
                { failure ->
                    loadState.value = LoadAreaView.State.MAIN
                    sendEvent(Event.ShowSaveLocationFailure(failure))
                }, {
                    loadState.value = LoadAreaView.State.MAIN
                    sendEvent(Event.Finish)
                }
            )
        }
        else {
            loadState.value = LoadAreaView.State.MAIN
        }
    }

}