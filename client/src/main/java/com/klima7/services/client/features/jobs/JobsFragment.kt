package com.klima7.services.client.features.jobs

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobsBinding
import com.klima7.services.client.features.offers.OffersActivity
import com.klima7.services.client.features.offers.OffersFragment
import com.klima7.services.common.extensions.isLarge
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

    private val offersScreenLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        this.onOffersScreenFinish(result)
    }

    override fun init() {
        super.init()

        jobsAdapter = JobsAdapter(this)
        binding.jobsLoadList.adapter = jobsAdapter
        val columns = if(requireContext().isLarge()) 2 else 1
        binding.jobsLoadList.layoutManager = StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)

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

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    override fun onJobClicked(job: Job) {
        viewModel.jobClicked(job.id)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobsViewModel.Event.RefreshJobs -> refreshJobs()
            is JobsViewModel.Event.ShowOffersScreen -> showOffersScreen(event.jobId)
        }
    }

    private fun refreshJobs() {
        jobsAdapter.refresh()
    }

    private fun showOffersScreen(jobId: String) {
        val intent = Intent(requireContext(), OffersActivity::class.java)
        val bundle = bundleOf(
            "jobId" to jobId,
            "exit" to "slideRight",
        )
        intent.putExtras(bundle)
        offersScreenLauncher.launch(intent)
        Animatoo.animateSlideLeft(requireActivity())
    }

    private fun onOffersScreenFinish(result: ActivityResult) {
        val intent = result.data
        val wasFinished = intent?.getBooleanExtra(OffersFragment.RESULT_FINISHED, false) ?: false
        if(wasFinished) {
            jobsAdapter.refresh()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}
