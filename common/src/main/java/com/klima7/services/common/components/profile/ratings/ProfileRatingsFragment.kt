package com.klima7.services.common.components.profile.ratings

import android.content.Intent
import androidx.core.os.bundleOf
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.R
import com.klima7.services.common.components.ratings.RatingsActivity
import com.klima7.services.common.databinding.FragmentProfileRatingsBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileRatingsFragment: BaseFragment<FragmentProfileRatingsBinding>() {

    override val layoutId = R.layout.fragment_profile_ratings
    override val viewModel: ProfileRatingsViewModel by viewModel()

    override fun init() {
        super.init()

        val latestViewModel = childFragmentManager
            .findFragmentById(R.id.profile_ratings_latest_fragment)
            ?.getViewModel<ProfileRatingsLatestViewModel>()
        viewModel.expert.observe(viewLifecycleOwner) { expert ->
            latestViewModel?.setExpertUid(expert.uid)
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ProfileRatingsViewModel.Event.ShowRatingsScreen ->
                showRatingsScreen(event.expertUid, event.expertName)
        }
    }

    private fun showRatingsScreen(expertUid: String, expertName: String) {
        val intent = Intent(activity, RatingsActivity::class.java)
        val extras = bundleOf(
            "expertUid" to expertUid,
            "expertName" to expertName,
            "exit" to "slideDown"
        )
        intent.putExtras(extras)
        startActivity(intent)
        Animatoo.animateSlideUp(requireActivity())
    }
}