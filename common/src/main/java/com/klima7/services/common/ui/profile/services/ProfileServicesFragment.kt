package com.klima7.services.common.ui.profile.services

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileServicesBinding
import com.klima7.services.common.ui.base.BaseLoadFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileServicesFragment: BaseLoadFragment<FragmentProfileServicesBinding>() {

    override val layoutId = R.layout.fragment_profile_services
    override val viewModel: ProfileServicesViewModel by viewModel()

}