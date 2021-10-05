package com.klima7.services.expert.features.setup

import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSetupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetupFragment: BaseFragment<FragmentSetupBinding>() {

    override val layoutId = R.layout.fragment_setup
    override val viewModel: SetupViewModel by viewModel()

    override fun init() {
        binding.setupToolbar.title = "Uzupełnienie informacji"
    }
}