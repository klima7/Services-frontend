package com.klima7.services.client.features.jobsetup.details

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobDetailsBinding
import com.klima7.services.client.features.jobsetup.JobSetupViewModel
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobDetailsFragment: BaseFragment<FragmentJobDetailsBinding>() {

    override val layoutId = R.layout.fragment_job_details
    override val viewModel: JobDetailsViewModel by viewModel()

    private val parentViewModel: JobSetupViewModel by lazy {
        requireParentFragment().requireParentFragment().getViewModel()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobDetailsViewModel.Event.ShowJobCreatedScreen -> showJobCreatedScreen()
        }
    }

    private fun showJobCreatedScreen() {
        parentViewModel.showCreatedScreen()
    }

}