package com.klima7.services.common.ui.profile.contact

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileContactBinding
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileContactFragment: BaseFragment<FragmentProfileContactBinding>() {

    override val layoutId = R.layout.fragment_profile_contact
    override val viewModel: ProfileContactViewModel by viewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            if(expert != null) {
                viewModel.setInfo(expert.info)
            }
        }
    }

}