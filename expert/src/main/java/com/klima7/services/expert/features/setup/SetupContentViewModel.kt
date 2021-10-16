package com.klima7.services.expert.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.ui.loadable.LoadableViewModel
import kotlinx.coroutines.launch

class SetupContentViewModel(
    private val getCurrentExpertSetupStateUC: GetCurrentExpertSetupStateUC
): LoadableViewModel() {

    val infoSetup = MutableLiveData(false)
    val servicesSetup = MutableLiveData(false)
    val locationSetup = MutableLiveData(false)

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
        viewModelScope.launch {
            getCurrentExpertSetupStateUC.execute().foldS({failure ->
                showFailure(failure)
            }, { result ->
                infoSetup.value = result.infoSetup
                servicesSetup.value = result.servicesSetup
                locationSetup.value = result.locationSetup
                showMain()
            })
        }
    }

}