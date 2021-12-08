package com.klima7.services.common.components.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.klima7.services.common.R
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.databinding.FragmentLoginBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseLoginFragment(
    private val subtitle: Int
): BaseFragment<FragmentLoginBinding>() {

    companion object {
        const val LOGIN_FAILURE_DIALOG_KEY = "LOGIN_FAILURE_DIALOG_KEY"
    }

    override val layoutId = R.layout.fragment_login
    abstract override val viewModel: BaseLoginViewModel

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun init() {
        super.init()

        binding.splashSubtitle.text = resources.getString(subtitle)

        childFragmentManager.setFragmentResultListener(LOGIN_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryLoginClicked()
            }
        }
    }

    override fun onFirstCreation() {
        super.onFirstCreation()

        childFragmentManager
            .beginTransaction()
            .add(R.id.login_decoration_holder, createDecorationFragment())
            .commit()
    }

    private fun signIn() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(viewModel.providers)
            .setLogo(R.drawable.logo)
            .setTheme(R.style.LoginTheme)
            .setAlwaysShowSignInMethodScreen(true)
            .setIsSmartLockEnabled(false)
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
            BaseLoginViewModel.Event.LaunchSignIn -> signIn()
            BaseLoginViewModel.Event.Finish -> finish()
            BaseLoginViewModel.Event.ShowFailure -> showFailure()
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

    protected abstract fun createDecorationFragment(): Fragment
}
