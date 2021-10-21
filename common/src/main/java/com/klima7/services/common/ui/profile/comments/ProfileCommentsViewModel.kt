package com.klima7.services.common.ui.profile.comments

import com.klima7.services.common.ui.base.BaseViewModel

class ProfileCommentsViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowCommentsScreen: Event()
    }

    fun showAllCommentsClicked() {
        sendEvent(Event.ShowCommentsScreen)
    }

}