package com.klima7.services.common.components.splash

import androidx.activity.result.contract.ActivityResultContracts
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentSplashBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel


abstract class BaseSplashFragment(
    private val subtitle: Int
): BaseFragment<FragmentSplashBinding>() {

    override val layoutId = R.layout.fragment_splash
    abstract override val viewModel: BaseSplashViewModel

    protected val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        this.onLoginResult()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        val offerId = arguments?.getString("offerId")
        if(offerId != null) {
            binding.splashMotionLayout.progress = 1f
        }
        viewModel.started(offerId)
    }

    override fun init() {
        super.init()
        binding.splashSubtitle.text = resources.getString(subtitle)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            BaseSplashViewModel.Event.ShowLoginScreen -> showLoginScreen()
            is BaseSplashViewModel.Event.ShowHomeScreen -> showHomeScreen(event.offerId)
            BaseSplashViewModel.Event.ShowSetupScreen -> showSetupScreen()
        }
    }

    private fun onLoginResult() {
        viewModel.loginActivityFinished()
    }

    protected abstract fun showLoginScreen()

    protected abstract fun showSetupScreen();

    protected abstract fun showHomeScreen(offerId: String?);

}
