package com.klima7.services.expert.features.services

import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServicesContentFragment: FailurableFragment<FragmentServicesBinding>() {

    override val layoutId = R.layout.fragment_services
    override val viewModel: ServicesContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.servicesStarted()
    }
}
