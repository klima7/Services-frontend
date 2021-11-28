package com.klima7.services.common.components.comment

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentCommentBinding
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentFragment: BaseFragment<FragmentCommentBinding>() {

    override val layoutId = R.layout.fragment_comment
    override val viewModel: CommentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val jobId = arguments?.getString("commentId") ?: throw Error("commentId argument not supplied")
        val comment = arguments?.getParcelable<Rating>("rating")
        viewModel.start(jobId, comment)
    }

    override fun init() {
        super.init()
        binding.commentToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

}