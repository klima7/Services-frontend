package com.klima7.services.common.components.profile.header

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileHeaderBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileHeaderFragment: BaseFragment<FragmentProfileHeaderBinding>() {

    override val layoutId = R.layout.fragment_profile_header
    override val viewModel: ProfileHeaderViewModel by viewModel()

    override fun init() {
        super.init()

        viewModel.profileImageUrl.observe(viewLifecycleOwner) { profileImageUrl ->
            binding.profileHeaderAvatar.setProfileImage(profileImageUrl)
        }

    }

}