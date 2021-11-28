package com.klima7.services.common.components.profile.average

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileRatingBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileAverageFragment: BaseFragment<FragmentProfileRatingBinding>() {

    override val layoutId = R.layout.fragment_profile_rating
    override val viewModel: ProfileAverageViewModel by viewModel()

    override fun init() {
        super.init()

        viewModel.rating.observe(viewLifecycleOwner) { rating ->
            binding.profileRatingRateBar.rating = rating
        }
    }

}