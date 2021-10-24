package com.klima7.services.common.components.splash

import androidx.activity.result.contract.ActivityResultContracts
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentSplashBinding
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.common.platform.BaseViewModel


abstract class BaseSplashFragment(
    private val subtitle: Int
): BaseLoadFragment<FragmentSplashBinding>() {

    override val layoutId = R.layout.fragment_splash
    abstract override val viewModel: BaseSplashViewModel

    protected val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        this.onLoginResult()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
    }

    override fun init() {
        super.init()
        binding.splashSubtitle.text = resources.getString(subtitle)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            BaseSplashViewModel.Event.ShowLoginScreen -> showLoginScreen()
            BaseSplashViewModel.Event.ShowHomeScreen -> showHomeScreen()
            BaseSplashViewModel.Event.ShowSetupScreen -> showSetupScreen()
        }
    }

    private fun onLoginResult() {
        viewModel.loginActivityFinished()
    }

    protected abstract fun showLoginScreen()

    protected abstract fun showSetupScreen();

    protected abstract fun showHomeScreen();

}
