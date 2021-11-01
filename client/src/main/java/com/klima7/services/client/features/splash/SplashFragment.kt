package com.klima7.services.client.features.splash

import android.content.Intent
import com.klima7.services.client.R
import com.klima7.services.client.features.home.HomeActivity
import com.klima7.services.client.features.login.LoginActivity
import com.klima7.services.client.features.setup.SetupActivity
import com.klima7.services.common.components.splash.BaseSplashFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment: BaseSplashFragment(R.string.app_subtitle) {

    override val viewModel: SplashViewModel by viewModel()

    override fun showLoginScreen() {
        val intent = Intent(activity, LoginActivity::class.java)
        loginLauncher.launch(intent)
    }

    override fun showSetupScreen() {
        val intent = Intent(activity, SetupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }

    override fun showHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }

}
