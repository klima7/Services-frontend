package com.klima7.services.expert.features.home

import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentHomeBinding
import com.klima7.services.expert.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

}