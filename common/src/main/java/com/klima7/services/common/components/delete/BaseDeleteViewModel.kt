package com.klima7.services.common.components.delete

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseLoadViewModel

open class BaseDeleteViewModel(
    private val deleteUserUC: BaseDeleteUserUC
): BaseLoadViewModel() {

    private var confirmText: String? = null
    val typedText = MutableLiveData<String>()
    val buttonEnabled = typedText.map { confirmText != null && it == confirmText }

    sealed class Event: BaseEvent() {
        object Finish: Event()
        object ShowSplashScreen: Event()
        class ShowDeleteFailure(val failure: Failure): Event()
    }

    fun start() {
        showMain()
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
        showPending()
        deleteUserUC.start(
            viewModelScope,
            None(),
            { failure ->
                showMain()
                sendEvent(Event.ShowDeleteFailure(failure))
            },
            {
                showMain()
                sendEvent(Event.ShowSplashScreen)
            }
        )
    }

}
