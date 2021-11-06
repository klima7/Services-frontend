package com.klima7.services.client.features.jobs

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobsBinding
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseLoadFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobsFragment : BaseLoadFragment<FragmentJobsBinding>(), JobsAdapter.OnJobListener {

    override val layoutId = R.layout.fragment_jobs
    override val viewModel: JobsViewModel by viewModel()

    lateinit var jobsAdapter: JobsAdapter

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

    override fun onJobClicked(job: Job) {
        Log.i("Hello", "Job clicked")
    }

}
