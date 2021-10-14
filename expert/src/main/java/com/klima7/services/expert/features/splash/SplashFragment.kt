package com.klima7.services.expert.features.splash

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.failfrag.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSplashBinding
import com.klima7.services.expert.features.home.HomeActivity
import com.klima7.services.expert.features.login.LoginActivity
import com.klima7.services.expert.features.setup.SetupActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment: FailurableFragment<FragmentSplashBinding>() {

    override val layoutId = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

    private val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        this.onLoginResult()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.splashStarted()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            SplashViewModel.Event.ShowLoginScreen -> showLoginScreen()
            SplashViewModel.Event.ShowHomeScreen -> showHomeScreen()
            SplashViewModel.Event.ShowSetupScreen -> showSetupScreen()
        }
    }

    private fun showLoginScreen() {
        val intent = Intent(activity, LoginActivity::class.java)
        loginLauncher.launch(intent)
    }

    private fun onLoginResult() {
        viewModel.loginActivityFinished()
    }

    private fun showSetupScreen() {
        val intent = Intent(activity, SetupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        loginLauncher.launch(intent)
    }


}
