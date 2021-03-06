package com.klima7.services.expert.features.jobs.base

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.klima7.services.common.extensions.isLarge
import com.klima7.services.common.models.ExpertJob
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsListBinding
import com.klima7.services.expert.features.job.JobActivity
import com.klima7.services.expert.features.job.JobFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


abstract class BaseJobsListFragment : BaseFragment<FragmentJobsListBinding>(), JobsAdapter.OnJobListener {

    override val layoutId = R.layout.fragment_jobs_list
    abstract override val viewModel: BaseJobsListViewModel

    lateinit var jobsAdapter: JobsAdapter

    private val jobScreenLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        this.onJobScreenFinished(result)
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    override fun init() {
        super.init()

        jobsAdapter = JobsAdapter(this)
        binding.jobsLoadList.adapter = jobsAdapter
        val columns = if(requireContext().isLarge()) 2 else 1
        binding.jobsLoadList.layoutManager = StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                jobsAdapter.submitData(pagingData)
            }
        }
    }

    fun refresh() {
        viewModel.refresh()
    }

    override fun onJobClicked(expertJob: ExpertJob) {
        val intent = Intent(requireContext(), JobActivity::class.java)
        val bundle = bundleOf("jobId" to expertJob.job.id)
        intent.putExtras(bundle)
        jobScreenLauncher.launch(intent)
    }

    protected fun attachItemTouchHelper(itemTouchHelper: ItemTouchHelper) {
        itemTouchHelper.attachToRecyclerView(binding.jobsLoadList.recycler)
    }

    private fun onJobScreenFinished(activityResult: ActivityResult) {
        val state = activityResult.data?.getStringExtra(JobFragment.RESULT_STATE_KEY) ?: JobFragment.RESULT_STATE_NOOP
        val jobId = activityResult.data?.getStringExtra(JobFragment.RESULT_JOB_KEY) ?: throw Exception("No job id")
        if(state == JobFragment.RESULT_STATE_ACCEPT || state == JobFragment.RESULT_STATE_REJECT) {
            viewModel.jobChanged(jobId)
        }
    }
}
