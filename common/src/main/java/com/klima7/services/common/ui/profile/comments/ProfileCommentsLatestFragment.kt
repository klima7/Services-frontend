package com.klima7.services.common.ui.profile.comments

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileLatestCommentsBinding
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCommentsLatestFragment: BaseFragment<FragmentProfileLatestCommentsBinding>() {

    override val layoutId = R.layout.fragment_profile_latest_comments
    override val viewModel: ProfileCommentsLatestViewModel by viewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        val adapter = ProfileCommentsLatestAdapter(requireContext())
        binding.profileCommentsList.adapter = adapter

        viewModel.ratings.observe(viewLifecycleOwner) { ratings ->
            adapter.setRatings(ratings)
        }

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            viewModel.setExpertUid(expert.uid)
        }
    }
}