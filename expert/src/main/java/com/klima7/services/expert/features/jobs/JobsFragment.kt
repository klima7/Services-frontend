package com.klima7.services.expert.features.jobs

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsBinding
import com.klima7.services.expert.features.jobs.base.BaseJobsListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobsFragment : BaseFragment<FragmentJobsBinding>(), TabLayout.OnTabSelectedListener {

    override val layoutId = R.layout.fragment_jobs
    override val viewModel: JobsViewModel by viewModel()
    private lateinit var navController: NavController

    override fun init() {
        super.init()

        val navHostFragment = childFragmentManager.findFragmentById(R.id.jobs_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        selectProperTab()

        binding.jobsTabs.addOnTabSelectedListener(this)

        binding.jobsRefreshLayout.setOnRefreshListener {
            binding.jobsRefreshLayout.isRefreshing = false
            val fragment = navHostFragment.childFragmentManager.fragments.get(0) as BaseJobsListFragment
            fragment.refresh()
        }

        viewModel.refreshEnabled.observe(viewLifecycleOwner) { refreshEnabled ->
            binding.jobsRefreshLayout.isEnabled = refreshEnabled
        }
    }

    private fun selectProperTab() {
        val tabIndex = when (viewModel.selectedTab.value) {
            JobsViewModel.Tab.New -> 0
            JobsViewModel.Tab.Rejected -> 1
            else -> 0
        }
        binding.jobsTabs.getTabAt(tabIndex)?.select()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobsViewModel.Event.ShowNewTab -> showNewTab()
            JobsViewModel.Event.ShowRejectedTab -> showRejectedTab()
        }
    }

    private fun showNewTab() {
        navController.navigate(R.id.newJobsListFragment)
    }

    private fun showRejectedTab() {
        navController.navigate(R.id.rejectedJobsListFragment)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if(tab == null)
            return

        if(tab.position == 0) {
            viewModel.newTabSelected()
        }
        else if(tab.position == 1) {
            viewModel.rejectedTabSelected()
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}
