package com.klima7.services.common.components.profile.description

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileDescriptionBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileDescriptionFragment: BaseFragment<FragmentProfileDescriptionBinding>() {

    override val layoutId = R.layout.fragment_profile_description
    override val viewModel: ProfileDescriptionViewModel by viewModel()

}