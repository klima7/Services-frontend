package com.klima7.services.expert.features.jobs.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsListBinding
import com.klima7.services.expert.features.job.JobActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


abstract class BaseJobsListFragment : BaseLoadFragment<FragmentJobsListBinding>(), JobsAdapter.OnJobListener {

    override val layoutId = R.layout.fragment_jobs_list
    abstract override val viewModel: BaseJobsListViewModel

    lateinit var jobsAdapter: JobsAdapter

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    @ExperimentalCoroutinesApi
    override fun init() {
        super.init()

        jobsAdapter = JobsAdapter(this)
        binding.jobsLoadList.adapter = jobsAdapter
        binding.jobsLoadList.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                jobsAdapter.submitData(pagingData)
            }
        }
    }

    fun refresh() {
        viewModel.refresh()
    }

    override fun onJobClicked(job: Job) {
        val intent = Intent(requireContext(), JobActivity::class.java)
        val bundle = bundleOf("jobId" to job.id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    protected fun attachItemTouchHelper(itemTouchHelper: ItemTouchHelper) {
        itemTouchHelper.attachToRecyclerView(binding.jobsLoadList.recycler)
    }
}
