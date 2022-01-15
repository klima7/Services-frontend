package com.klima7.services.common.components.login

import com.firebase.ui.auth.AuthUI
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseLoginViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object LaunchSignIn: Event()
        object Finish: Event()
        object ShowFailure: Event()
    }

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().setRequireName(false).build(),
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )

    fun loginButtonClicked() {
        sendEvent(Event.LaunchSignIn)
    }

    fun retryLoginClicked() {
        sendEvent(Event.LaunchSignIn)
    }

    fun loginSuccess() {
        sendEvent(Event.Finish)
    }

    fun loginFailure() {
        sendEvent(Event.ShowFailure)
    }

}