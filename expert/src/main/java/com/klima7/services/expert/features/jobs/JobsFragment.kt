package com.klima7.services.expert.features.jobs

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobsFragment: BaseLoadFragment<FragmentJobsBinding>() {

    override val layoutId = R.layout.fragment_jobs
    override val viewModel: JobsViewModel by viewModel()

    lateinit var mAdapter: JobsAdapter

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    override fun init() {
        super.init()

        mAdapter = JobsAdapter()
        binding.jobsLoadList.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.jobsLoadList.recycler)

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }

    }

    val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.absoluteAdapterPosition
            val job = mAdapter.getJob(position)
            Log.i("Hello", "Removed $job")
            viewModel.hideId(job!!.id)
            viewModel.fade()
        }
    }
}