package com.klima7.services.common.ui.profile.comments

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileLatestCommentsBinding
import com.klima7.services.common.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCommentsLatestFragment: BaseFragment<FragmentProfileLatestCommentsBinding>() {

    override val layoutId = R.layout.fragment_profile_latest_comments
    override val viewModel: ProfileCommentsLatestViewModel by viewModel()

}