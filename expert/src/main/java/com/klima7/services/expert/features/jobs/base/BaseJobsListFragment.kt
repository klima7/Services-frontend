package com.klima7.services.expert.features.jobs.base

import android.content.Intent
import android.graphics.Canvas
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsListBinding
import com.klima7.services.expert.features.job.JobActivity
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


abstract class BaseJobsListFragment : BaseLoadFragment<FragmentJobsListBinding>(), JobsAdapter.OnJobListener, TabLayout.OnTabSelectedListener {

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

        binding.jobsTabs.addOnTabSelectedListener(this)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when (event) {
            BaseJobsListViewModel.Event.ShowRejectFailure -> showRejectFailure()
        }
    }

    private fun showRejectFailure() {
        Toast.makeText(requireContext(), "Odrzucenie zlecenia się nie powiodło", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onJobClicked(job: Job) {
        val intent = Intent(requireContext(), JobActivity::class.java)
        val bundle = bundleOf("jobId" to job.id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.i("Hello", "Tab $tab selected")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        Log.i("Hello", "Tab $tab unselected")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        Log.i("Hello", "Tab $tab reselected")
    }
}
