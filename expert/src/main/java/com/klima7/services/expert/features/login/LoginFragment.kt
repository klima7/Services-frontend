package com.klima7.services.expert.features.login

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun signIn() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(viewModel.providers)
            .setLogo(R.drawable.logo)
            .setTheme(R.style.login_theme)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        when {
            result.resultCode == AppCompatActivity.RESULT_OK -> {
                val user = FirebaseAuth.getInstance().currentUser
                Log.i("Hello", "Login success")
                requireActivity().finish()
            }
            response == null -> {
                Log.i("Hello", "Login cancelled",)
            }
            else -> {
                Log.i("Hello", "Login failed", response.error)
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            LoginViewModel.Event.LaunchSignIn -> signIn()
        }
    }
}
