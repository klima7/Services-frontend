package com.klima7.services.common.ui.profile.rating

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileHeaderBinding
import com.klima7.services.common.databinding.FragmentProfileRatingBinding
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileRatingFragment: BaseFragment<FragmentProfileRatingBinding>() {

    override val layoutId = R.layout.fragment_profile_rating
    override val viewModel: ProfileRatingViewModel by viewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            if(expert != null) {
                viewModel.setExpert(expert)
            }
        }

        viewModel.rating.observe(viewLifecycleOwner) { rating ->
            binding.profileRatingRateBar.rating = rating
        }

    }

}