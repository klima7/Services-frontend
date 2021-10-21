package com.klima7.services.common.ui.comment

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentCommentBinding
import com.klima7.services.common.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentFragment: BaseFragment<FragmentCommentBinding>() {

    override val layoutId = R.layout.fragment_comment
    override val viewModel: CommentViewModel by viewModel()

}