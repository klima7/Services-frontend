package com.klima7.services.expert.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.ui.base.BaseLoadViewModel

class SetupViewModel(
    private val getCurrentExpertSetupStateUC: GetCurrentExpertSetupStateUC
): BaseLoadViewModel() {

    val setupState = MutableLiveData<ExpertSetupState>()

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowWorkingAreaScreen: Event()
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

    fun servicesClicked() {
        sendEvent(Event.ShowServicesScreen)
    }

    fun locationClicked() {
        sendEvent(Event.ShowWorkingAreaScreen)
    }

    private fun loadContent() {
        showLoading()
        getCurrentExpertSetupStateUC.start(
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