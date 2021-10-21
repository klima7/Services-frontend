package com.klima7.services.common.ui.profile.comments

import android.util.Log
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileCommentsBinding
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCommentsFragment: BaseFragment<FragmentProfileCommentsBinding>() {

    override val layoutId = R.layout.fragment_profile_comments
    override val viewModel: ProfileCommentsViewModel by viewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            if(expert != null) {
                binding.commentsCount = expert.commentsCount
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            ProfileCommentsViewModel.Event.ShowCommentsScreen -> showCommentsScreen()
        }
    }

    private fun showCommentsScreen() {
        Log.i("Hello", "Showing comments screen")
    }
}