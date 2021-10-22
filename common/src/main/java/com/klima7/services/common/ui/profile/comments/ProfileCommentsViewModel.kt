package com.klima7.services.common.ui.profile.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.ui.base.BaseViewModel

class ProfileCommentsViewModel: BaseViewModel() {

    val commentsCount = MutableLiveData(0)
    val showMoreVisible = commentsCount.map { it != 0 }

    sealed class Event: BaseEvent() {
        object ShowCommentsScreen: Event()
    }

    fun showAllCommentsClicked() {
        sendEvent(Event.ShowCommentsScreen)
    }

    fun setCommentsCount(commentsCount: Int) {
        this.commentsCount.value = commentsCount
    }

}