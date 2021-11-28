package com.klima7.services.common.components.profile.comments

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.components.rating.RatingActivity
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.databinding.FragmentProfileLatestCommentsBinding
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseLoadFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCommentsLatestFragment: BaseLoadFragment<FragmentProfileLatestCommentsBinding>(),
    RatingItem.Listener {

    override val layoutId = R.layout.fragment_profile_latest_comments
    override val viewModel: ProfileCommentsLatestViewModel by viewModel()

    private val groupieAdapter = GroupieAdapter()

    override fun init() {
        super.init()

        binding.profileCommentsRecycler.apply {
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
