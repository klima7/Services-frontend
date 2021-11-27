package com.klima7.services.common.components.comment

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentCommentBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentFragment: BaseFragment<FragmentCommentBinding>() {

    override val layoutId = R.layout.fragment_comment
    override val viewModel: CommentViewModel by viewModel()

    private lateinit var jobId: String

    override fun onFirstCreation() {
        super.onFirstCreation()
        jobId = arguments?.getString("commentId") ?: throw Error("commentId argument not supplied")
        viewModel.start(jobId)
    }

    override fun init() {
        super.init()
        binding.commentToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

}