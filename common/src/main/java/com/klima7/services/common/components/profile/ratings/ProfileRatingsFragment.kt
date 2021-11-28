package com.klima7.services.common.components.profile.ratings

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.R
import com.klima7.services.common.components.rating.RatingActivity
import com.klima7.services.common.components.ratings.RatingsActivity
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.databinding.FragmentProfileRatingsBinding
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileRatingsFragment: BaseFragment<FragmentProfileRatingsBinding>(), RatingItem.Listener {

    override val layoutId = R.layout.fragment_profile_ratings
    override val viewModel: ProfileRatingsViewModel by viewModel()
    private val groupieAdapter = GroupieAdapter()

    override fun init() {
        super.init()

        binding.profileRatingsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupieAdapter
        }

        viewModel.ratings.observe(viewLifecycleOwner, this::updateRatings)
    }

    private fun updateRatings(newRatings: List<Rating>) {
        groupieAdapter.clear()
        newRatings.forEach { rating ->
            groupieAdapter += RatingItem(rating, this)
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

    override fun onRatingClicked(rating: Rating, ratingView: RatingView) {
        val intent = Intent(requireContext(), RatingActivity::class.java)
        val bundle = bundleOf(
            "ratingId" to rating.id,
            "rating" to rating,
        )
        intent.putExtras(bundle)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            ratingView,
            "rating"
        )
        startActivity(intent, options.toBundle())
    }
}