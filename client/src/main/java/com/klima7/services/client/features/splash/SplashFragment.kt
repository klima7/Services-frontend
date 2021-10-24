package com.klima7.services.client.features.splash

import com.klima7.services.client.R
import com.klima7.services.common.components.splash.BaseSplashFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment: BaseSplashFragment(R.string.app_subtitle) {

    override val viewModel: SplashViewModel by viewModel()

    override fun showLoginScreen() {

    }

    override fun showSetupScreen() {

    }

    override fun showHomeScreen() {

    }

}
