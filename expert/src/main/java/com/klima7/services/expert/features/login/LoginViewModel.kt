package com.klima7.services.expert.features.login

import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.klima7.services.common.lib.base.BaseViewModel

class LoginViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        object LaunchSignIn: Event()
        object Finish: Event()
        object ShowFailure: Event()
    }

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
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