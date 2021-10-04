package com.klima7.services.expert.features.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSplashBinding
import com.klima7.services.expert.features.home.HomeActivity
import com.klima7.services.expert.features.login.LoginActivity
import com.klima7.services.expert.features.setup.SetupActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment: BaseFragment<FragmentSplashBinding>() {

    override val layoutId = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

    private val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        this.onLoginResult(res)
    }

    override fun init() {
        viewModel.splashStarted()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
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

    private fun onLoginResult(res: ActivityResult) {
        Log.i("Hello", "Login activity finished")
        viewModel.loginActivityFinished()
    }

    private fun showSetupScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
//        val intent = Intent(activity, SetupActivity::class.java)
        loginLauncher.launch(intent)
    }

    private fun showHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        loginLauncher.launch(intent)
    }


}
