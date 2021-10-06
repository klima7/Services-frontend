package com.klima7.services.expert.features.services

import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import com.klima7.services.expert.features.info.InfoContentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServicesContentFragment: FailurableFragment<FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_info
    override val viewModel: InfoContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.doSomething()
    }
}
