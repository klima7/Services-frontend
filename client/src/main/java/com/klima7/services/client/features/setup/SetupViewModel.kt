package com.klima7.services.client.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.usecases.SignOutUC

class SetupViewModel(
    private val getCurrentClientSetupStateUC: GetCurrentClientSetupStateUC,
    private val signOutUC: SignOutUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
        object ShowSplashScreen: Event()
    }

    val setupState = MutableLiveData<ClientSetupState>()
    val continueButtonEnabled = setupState.map { it.infoSetup }

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

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

    fun logoutClicked() {
        signOut()
    }

    private fun signOut() {
        signOutUC.start(
            viewModelScope,
            None(),
            { },
            {
                sendEvent(Event.ShowSplashScreen)
            }
        )
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getCurrentClientSetupStateUC.start(
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