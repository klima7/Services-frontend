package com.klima7.services.common.lib.fragments

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel

class FailurableWrapperViewModel: BaseViewModel() {

    val errorVisible = MutableLiveData(false)

    sealed class Event: BaseEvent() {
        object RefreshMainFragment: Event()
    }

    fun showFailureReceived(failure: Failure) {
        errorVisible.value = true
    }

    fun refreshClicked() {
        errorVisible.value = false
        sendEvent(Event.RefreshMainFragment)
    }

}