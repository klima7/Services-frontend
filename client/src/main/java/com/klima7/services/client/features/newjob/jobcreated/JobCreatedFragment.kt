package com.klima7.services.client.features.newjob.jobcreated

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobCreatedBinding
import com.klima7.services.client.features.newjob.newjob.NewJobViewModel
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobCreatedFragment: BaseFragment<FragmentJobCreatedBinding>() {

    override val layoutId = R.layout.fragment_job_created
    override val viewModel: JobCreatedViewModel by viewModel()

    private val parentViewModel: NewJobViewModel by lazy {
        requireParentFragment().requireParentFragment().getViewModel()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobCreatedViewModel.Event.ShowJobsScreen -> showJobsScreen()
        }
    }

    private fun showJobsScreen() {
        parentViewModel.showJobsScreen()
    }

}
