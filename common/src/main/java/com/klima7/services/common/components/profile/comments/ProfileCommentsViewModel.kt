package com.klima7.services.common.components.profile.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseViewModel

class ProfileCommentsViewModel: BaseViewModel() {

    val expert = MutableLiveData<Expert>()
    val commentsCount = expert.map { expert -> expert.commentsCount }
    val showMoreVisible = commentsCount.map { commentsCount -> commentsCount != 0 }

    sealed class Event: BaseEvent() {
        data class ShowCommentsScreen(val expertUid: String, val expertName: String): Event()
    }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

    fun showAllCommentsClicked() {
        val uid = expert.value?.uid
        val name = expert.value?.info?.name
        if(uid != null && name != null)
        sendEvent(Event.ShowCommentsScreen(uid, name))
    }

}