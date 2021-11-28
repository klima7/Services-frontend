package com.klima7.services.common.components.ratings

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.components.comment.CommentActivity
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.databinding.FragmentRatingsBinding
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RatingsFragment:
    BaseFragment<FragmentRatingsBinding>(),
    RatingsAdapter.OnRatingListener {

    override val layoutId = R.layout.fragment_ratings
    override val viewModel: RatingsViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val expertId = arguments?.getString("expertUid") ?: throw Error("expertUid argument not supplied")
        val expertName = arguments?.getString("expertName") ?: ""
        viewModel.start(expertId, expertName)
    }

    override fun init() {
        super.init()

        binding.ratingsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val adapter = RatingsAdapter(this)
        binding.ratingsLoadList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(lifecycle, pagingData)
            }
        }
    }

    override fun onRatingClicked(rating: Rating, ratingView: RatingView) {
        val intent = Intent(requireContext(), CommentActivity::class.java)
        val bundle = bundleOf(
            "commentId" to rating.id,
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