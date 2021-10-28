package com.klima7.services.expert.features.jobs

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobsFragment: BaseLoadFragment<FragmentJobsBinding>() {

    override val layoutId = R.layout.fragment_jobs
    override val viewModel: JobsViewModel by viewModel()

    lateinit var jobsAdapter: JobsAdapter

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    override fun init() {
        super.init()

        jobsAdapter = JobsAdapter()
        binding.jobsLoadList.adapter = jobsAdapter
        binding.jobsLoadList.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.jobsLoadList.recycler)

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                jobsAdapter.submitData(pagingData)
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobsViewModel.Event.ShowRejectFailure -> showRejectFailure()
        }
    }

    private fun showRejectFailure() {
        Toast.makeText(requireContext(), "Odrzucenie zlecenia się nie powiodło", Toast.LENGTH_SHORT).show()
    }

    private val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.absoluteAdapterPosition
            val job = jobsAdapter.getJob(position)
            if(job != null) {
                viewModel.jobSwiped(job.id)
            }
        }
    }
}