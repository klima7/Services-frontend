package com.klima7.services.common.ui.profile.services

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileServicesBinding
import com.klima7.services.common.ui.loadable.LoadableFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileServicesContentFragment: LoadableFragment<FragmentProfileServicesBinding>() {

    override val layoutId = R.layout.fragment_profile_services
    override val viewModel: ProfileServicesContentViewModel by viewModel()

}