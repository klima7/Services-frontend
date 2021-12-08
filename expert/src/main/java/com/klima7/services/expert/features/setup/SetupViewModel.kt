package com.klima7.services.expert.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.usecases.SignOutExpertUC

class SetupViewModel(
    private val getCurrentExpertSetupStateUC: GetCurrentExpertSetupStateUC,
    private val signOutExpertUC: SignOutExpertUC,
): BaseViewModel() {

    val setupState = MutableLiveData<ExpertSetupState>()
    val continueButtonEnabled = setupState.map { it.infoSetup && it.locationSetup && it.servicesSetup }

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowWorkingAreaScreen: Event()
        object ShowSplashScreen: Event()
    }

    fun started() {
        loadContent()
    }

    fun refresh() {
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

    fun logoutClicked() {
        signOut()
    }

    private fun signOut() {
        signOutExpertUC.start(
            viewModelScope,
            None(),
            {
                // Do nothing - in current implementation sign out failure is impossible
            }, {
                sendEvent(Event.ShowSplashScreen)
            }
        )
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getCurrentExpertSetupStateUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            }, { setupState ->
                this.setupState.value = setupState
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}