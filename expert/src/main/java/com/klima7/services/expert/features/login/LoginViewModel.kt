package com.klima7.services.expert.features.login

import com.firebase.ui.auth.AuthUI
import com.klima7.services.common.lib.base.BaseViewModel

class LoginViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object LaunchSignIn: Event()
    }

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    fun launchLogin() {
        sendEvent(Event.LaunchSignIn)
    }

}