package com.klima7.services.expert.features.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment: BaseFragment<FragmentSplashBinding>() {

    override val layoutId = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

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
}
