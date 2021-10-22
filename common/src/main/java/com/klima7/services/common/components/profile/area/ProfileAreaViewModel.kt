package com.klima7.services.common.components.profile.area

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Coordinates
import com.klima7.services.common.models.WorkingArea
import com.klima7.services.common.platform.BaseViewModel

class ProfileAreaViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        class OpenLocationInGoogleMaps(val coords: Coordinates): Event()
    }

    val area = MutableLiveData<WorkingArea?>()
    val isSpecified = area.map { it != null }
    val name = area.map { it?.location?.name }
    val radius = area.map { it?.radius }

    fun setArea(area: WorkingArea?) {
        this.area.value = area
    }

    fun openInGoogleMapClicked() {
        val coords = area.value?.location?.coords
        if(coords != null) {
            sendEvent(Event.OpenLocationInGoogleMaps(coords))
        }
    }

}