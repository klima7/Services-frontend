package com.klima7.services.common.components.comments

import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.components.comment.CommentActivity
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.databinding.FragmentCommentsBinding
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.app.ActivityOptionsCompat
import com.blogspot.atifsoftwares.animatoolib.Animatoo


class CommentsFragment:
    BaseFragment<FragmentCommentsBinding>(),
    CommentsAdapter.OnCommentListener {

    override val layoutId = R.layout.fragment_comments
    override val viewModel: CommentsViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val expertId = arguments?.getString("expertUid") ?: throw Error("expertUid argument not supplied")
        val expertName = arguments?.getString("expertName") ?: ""
        viewModel.start(expertId, expertName)
    }

    override fun init() {
        super.init()

        binding.commentsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val adapter = CommentsAdapter(this)
        binding.commentsLoadList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(lifecycle, pagingData)
            }
        }
    }

    override fun onCommentClicked(rating: Rating, ratingView: RatingView) {
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