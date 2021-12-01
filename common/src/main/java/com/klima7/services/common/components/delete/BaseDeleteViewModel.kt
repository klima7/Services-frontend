package com.klima7.services.common.components.delete

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel

open class BaseDeleteViewModel(
    private val deleteUserUC: BaseDeleteUserUC
): BaseViewModel() {

    private var confirmText: String? = null
    val typedText = MutableLiveData<String>()
    val buttonEnabled = typedText.map { confirmText != null && it == confirmText }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    sealed class Event: BaseEvent() {
        object Finish: Event()
        object ShowSplashScreen: Event()
        class ShowDeleteFailure(val failure: Failure): Event()
    }

    fun setConfirmText(confirmText: String) {
        this.confirmText = confirmText
    }

    fun deleteClicked() {
        delete()
    }

    fun backClicked() {
        sendEvent(Event.Finish)
    }

    fun retryDeleteClicked() {
        delete()
    }

    private fun delete() {
        loadState.value = LoadAreaView.State.PENDING
        deleteUserUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowDeleteFailure(failure))
            },
            {
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowSplashScreen)
            }
        )
    }

}
