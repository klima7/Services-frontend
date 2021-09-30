package com.klima7.services.expert.features.splash

import com.klima7.services.common.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment: BaseFragment<FragmentSplashBinding>() {

    override val layoutId = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

}