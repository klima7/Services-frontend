package com.klima7.services.common.components.delete

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel

open class BaseDeleteViewModel(
    private val deleteUserUC: BaseDeleteUserUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object Finish: Event()
        object ShowSplashScreen: Event()
        class ShowDeleteFailure(val failure: Failure): Event()
    }

    private fun delete() {
        deleteUserUC.start(
            viewModelScope,
            None(),
            { failure ->
                sendEvent(Event.ShowDeleteFailure(failure))
            },
            {
                sendEvent(Event.ShowSplashScreen)
            }
        )
    }

}
