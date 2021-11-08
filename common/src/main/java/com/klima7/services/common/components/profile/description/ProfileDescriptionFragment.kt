package com.klima7.services.common.components.profile.description

import com.klima7.services.common.R
import com.klima7.services.common.components.profile.BaseProfileViewModel
import com.klima7.services.common.databinding.FragmentProfileDescriptionBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileDescriptionFragment: BaseFragment<FragmentProfileDescriptionBinding>() {

    override val layoutId = R.layout.fragment_profile_description
    override val viewModel: ProfileDescriptionViewModel by viewModel()

}