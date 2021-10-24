package com.klima7.services.client.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseLoadViewModel

class SetupViewModel(
    private val getCurrentClientSetupStateUC: GetCurrentClientSetupStateUC
): BaseLoadViewModel() {

    val setupState = MutableLiveData<ClientSetupState>()

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
    }

    fun started() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    fun continueClicked() {
        sendEvent(Event.ShowHomeScreen)
    }

    fun configDone() {
        loadContent()
    }

    fun infoClicked() {
        sendEvent(Event.ShowInfoScreen)
    }

    private fun loadContent() {
        showLoading()
        getCurrentClientSetupStateUC.start(
            viewModelScope,
            None(),
            { failure ->
                showFailure(failure)
            }, { setupState ->
                this.setupState.value = setupState
                showMain()
            }
        )
    }

}