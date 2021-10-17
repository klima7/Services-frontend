package com.klima7.services.expert.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.ui.loadable.LoadableViewModel
import kotlinx.coroutines.launch

class SetupContentViewModel(
    private val getCurrentExpertSetupStateUC: GetCurrentExpertSetupStateUC
): LoadableViewModel() {

    val setupState = MutableLiveData<ExpertSetupState>()

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowLocationScreen: Event()
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
        sendEvent(Event.ShowLocationScreen)
    }

    private fun loadContent() {
        showLoading()
        getCurrentExpertSetupStateUC.start(
            viewModelScope,
            BaseUC.NoParams(),
            { failure ->
                showFailure(failure)
            }, { setupState ->
                this.setupState.value = setupState
                showMain()
            }
        )
    }

}