package com.klima7.services.common.components.splash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
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

        val screen = arguments?.getString("screen") as String?
        val argument = arguments?.getString("argument") as String?
        Toast.makeText(requireContext(), "Screen: $screen; argument: $argument", Toast.LENGTH_LONG).show()

        if(screen != null) {
            binding.splashMotionLayout.progress = 1f
        }

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
