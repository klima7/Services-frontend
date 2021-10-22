package com.klima7.services.expert.features.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    companion object {
        const val LOGIN_FAILURE_DIALOG_KEY = "LOGIN_FAILURE_DIALOG_KEY"
    }

    override val layoutId = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun init() {
        super.init()
        childFragmentManager.setFragmentResultListener(LOGIN_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryLoginClicked();
            }
        }
    }

    private fun signIn() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(viewModel.providers)
            .setLogo(R.drawable.logo)
            .setTheme(R.style.LoginTheme)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        when {
            result.resultCode == AppCompatActivity.RESULT_OK -> {
                viewModel.loginSuccess()
            }
            response == null -> {
                // Login cancelled
            }
            else -> {
                viewModel.loginFailure()
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            LoginViewModel.Event.LaunchSignIn -> signIn()
            LoginViewModel.Event.Finish -> finish()
            LoginViewModel.Event.ShowFailure -> showFailure()
        }
    }

    private fun finish() {
        requireActivity().finish()
    }

    private fun showFailure() {
        val dialog = FailureDialogFragment.createRetry(
            LOGIN_FAILURE_DIALOG_KEY,
            "Logowanie się nie powiodło."
        )
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }
}
