package com.klima7.services.common.components.profile.ratings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseViewModel

class ProfileRatingsViewModel: BaseViewModel() {

    val expert = MutableLiveData<Expert>()
    val showMoreVisible = expert.map { expert -> expert.ratingsCount != 0 }

    sealed class Event: BaseEvent() {
        data class ShowRatingsScreen(val expertUid: String, val expertName: String): Event()
    }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

    fun showAllRatingsClicked() {
        val uid = expert.value?.uid
        val name = expert.value?.info?.name
        if(uid != null && name != null)
        sendEvent(Event.ShowRatingsScreen(uid, name))
    }

}