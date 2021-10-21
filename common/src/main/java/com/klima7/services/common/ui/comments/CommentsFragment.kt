package com.klima7.services.common.ui.comments

import android.util.Log
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentCommentsBinding
import com.klima7.services.common.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsFragment: BaseFragment<FragmentCommentsBinding>() {

    override val layoutId = R.layout.fragment_comments
    override val viewModel: CommentsViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val expertId = arguments?.getString("expertId")
        viewModel.start(expertId!!)
    }

}