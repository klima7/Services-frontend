package com.klima7.services.expert.features.info

import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment: FailurableFragment<FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_info
    override val viewModel: InfoViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.doSomething()
    }
}
