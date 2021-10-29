package com.klima7.services.expert.features.jobs

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobsFragment : BaseFragment<FragmentJobsBinding>(), TabLayout.OnTabSelectedListener {

    override val layoutId = R.layout.fragment_jobs
    override val viewModel: JobsViewModel by viewModel()
    private lateinit var navController: NavController

    override fun init() {
        super.init()

        binding.jobsTabs.addOnTabSelectedListener(this)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.jobs_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
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
