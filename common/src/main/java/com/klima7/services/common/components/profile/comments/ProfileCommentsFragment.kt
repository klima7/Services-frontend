package com.klima7.services.common.components.profile.comments

import android.content.Intent
import androidx.core.os.bundleOf
import com.klima7.services.common.R
import com.klima7.services.common.components.comments.CommentsActivity
import com.klima7.services.common.components.profile.ProfileViewModel
import com.klima7.services.common.databinding.FragmentProfileCommentsBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
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
                viewModel.setCommentsCount(expert.commentsCount)
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
        val intent = Intent(activity, CommentsActivity::class.java)
        val extras = bundleOf("expertId" to "GVUPHpMgt36NtVg9vsClFSaaOQQ7")
        intent.putExtras(extras)
        startActivity(intent)
    }
}