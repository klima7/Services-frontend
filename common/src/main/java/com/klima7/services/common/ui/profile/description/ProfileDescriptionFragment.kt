package com.klima7.services.common.ui.profile.description

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileDescriptionBinding
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.profile.ProfileViewModel
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