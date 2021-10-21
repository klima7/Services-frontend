package com.klima7.services.common.ui.comments

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentCommentsBinding
import com.klima7.services.common.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsFragment: BaseFragment<FragmentCommentsBinding>() {

    override val layoutId = R.layout.fragment_comments
    override val viewModel: CommentsViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val expertId = arguments?.getString("expertId")
        viewModel.start(expertId!!)
    }

    override fun init() {
        super.init()

        val adapter = CommentsAdapter()
        val recycler = binding.commentsRecycler
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        viewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }
    }
}