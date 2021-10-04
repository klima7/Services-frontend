package com.klima7.services.expert.features.setup

import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetupFragment: BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_setup
    override val viewModel: SetupViewModel by viewModel()

}