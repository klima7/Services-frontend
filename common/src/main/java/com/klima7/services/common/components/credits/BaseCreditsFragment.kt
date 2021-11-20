package com.klima7.services.common.components.credits

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentCreditsBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseCreditsFragment: BaseFragment<FragmentCreditsBinding>() {

    override val layoutId = R.layout.fragment_credits
    abstract override val viewModel: BaseViewModel

    override fun init() {
        super.init()
        binding.creditsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}