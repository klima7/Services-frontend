package com.klima7.services.common.ui.profile.comments

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.ui.base.BaseViewModel

class ProfileCommentsViewModel: BaseViewModel() {

    val commentsCount = MutableLiveData<Int>()

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