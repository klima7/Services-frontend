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
import com.klima7.services.expert.features.login.LoginActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(2000)
            viewModel.goToNextScreen(requireActivity())
        }
    }

    override fun init() {
        binding.splashRefreshButton.setOnClickListener { viewModel.goToNextScreen(requireActivity()) }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            SplashViewModel.Event.ShowLoginScreen -> showLoginScreen()
        }
    }

    private fun showLoginScreen() {
        val intent = Intent(activity, LoginActivity::class.java)
        loginLauncher.launch(intent)
    }

    private fun onLoginResult(res: ActivityResult) {
        Log.i("Hello", "Login activity finished")
        viewModel.goToNextScreen(requireActivity())

    }
}
