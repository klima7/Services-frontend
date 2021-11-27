package com.klima7.services.common.components.profile.comments

import android.content.Intent
import androidx.core.os.bundleOf
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.R
import com.klima7.services.common.components.comments.CommentsActivity
import com.klima7.services.common.databinding.FragmentProfileCommentsBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCommentsFragment: BaseFragment<FragmentProfileCommentsBinding>() {

    override val layoutId = R.layout.fragment_profile_comments
    override val viewModel: ProfileCommentsViewModel by viewModel()

    override fun init() {
        super.init()

        val latestVm = childFragmentManager.findFragmentById(R.id.profile_comments_latest_fragment)
            ?.getViewModel<ProfileCommentsLatestViewModel>()
        viewModel.expert.observe(viewLifecycleOwner) { expert ->
            latestVm?.setExpertUid(expert.uid)
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ProfileCommentsViewModel.Event.ShowCommentsScreen -> showCommentsScreen(event.expertUid)
        }
    }

    private fun showCommentsScreen(expertUid: String) {
        val intent = Intent(activity, CommentsActivity::class.java)
        val extras = bundleOf(
            "expertUid" to expertUid,
            "exit" to "slideDown"
        )
        intent.putExtras(extras)
        startActivity(intent)
        Animatoo.animateSlideUp(requireActivity())
    }
}