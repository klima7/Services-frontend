package com.klima7.services.client.features.jobs

import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobsBinding
import com.klima7.services.client.features.offers.OffersActivity
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobsFragment : BaseFragment<FragmentJobsBinding>(), JobsAdapter.OnJobListener {

    override val layoutId = R.layout.fragment_jobs
    override val viewModel: JobsViewModel by viewModel()

    lateinit var jobsAdapter: JobsAdapter

    override fun init() {
        super.init()

        jobsAdapter = JobsAdapter(this)
        binding.jobsLoadList.adapter = jobsAdapter
        binding.jobsLoadList.layoutManager = LinearLayoutManager(requireContext())

        binding.jobsRefreshLayout.setOnRefreshListener {
            binding.jobsRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                jobsAdapter.submitData(pagingData)
            }
        }
    }

    override fun onJobClicked(job: Job) {
        val intent = Intent(requireContext(), OffersActivity::class.java)
        val bundle = bundleOf("jobId" to job.id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobsViewModel.Event.RefreshJobs -> refreshJobs()
        }
    }

    private fun refreshJobs() {
        jobsAdapter.refresh()
    }
}
