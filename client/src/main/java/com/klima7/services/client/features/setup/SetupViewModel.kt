package com.klima7.services.client.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.core.None
import com.klima7.services.common.platform.BaseLoadViewModel
import com.klima7.services.common.usecases.SignOutUC

class SetupViewModel(
    private val getCurrentClientSetupStateUC: GetCurrentClientSetupStateUC,
    private val signOutUC: SignOutUC,
): BaseLoadViewModel() {

    val setupState = MutableLiveData<ClientSetupState>()
    val continueButtonEnabled = setupState.map { it.infoSetup }

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
        object ShowSplashScreen: Event()
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

    fun logoutClicked() {
        signOut()
    }

    private fun signOut() {
        signOutUC.start(
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