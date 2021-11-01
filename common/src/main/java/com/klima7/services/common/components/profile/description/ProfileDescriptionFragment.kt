package com.klima7.services.common.components.profile.description

import com.klima7.services.common.R
import com.klima7.services.common.components.profile.ProfileViewModel
import com.klima7.services.common.databinding.FragmentProfileDescriptionBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProfileDescriptionFragment: BaseFragment<FragmentProfileDescriptionBinding>() {

    override val layoutId = R.layout.fragment_profile_description
    override val viewModel = BaseViewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            if(expert != null) {
                binding.description = expert.info.description
            }
        }

    }

}