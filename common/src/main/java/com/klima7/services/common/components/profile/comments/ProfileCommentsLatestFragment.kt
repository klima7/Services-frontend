package com.klima7.services.common.components.profile.comments

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileLatestCommentsBinding
import com.klima7.services.common.platform.BaseLoadFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCommentsLatestFragment: BaseLoadFragment<FragmentProfileLatestCommentsBinding>() {

    override val layoutId = R.layout.fragment_profile_latest_comments
    override val viewModel: ProfileCommentsLatestViewModel by viewModel()

    override fun init() {
        super.init()

        val adapter = ProfileCommentsLatestAdapter(requireContext())
        binding.profileCommentsList.adapter = adapter

        viewModel.ratings.observe(viewLifecycleOwner) { ratings ->
            adapter.setRatings(ratings)
        }
    }
}