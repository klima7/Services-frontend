package com.klima7.services.client.features.job

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobFragment: BaseFragment<FragmentJobBinding>() {

    override val layoutId = R.layout.fragment_job
    override val viewModel: JobViewModel by viewModel()

    private lateinit var jobId: String

    override fun onFirstCreation() {
        super.onFirstCreation()
        jobId = arguments?.getString("jobId") ?: throw Error("JobId argument not supplied")
        viewModel.start(jobId)
    }

    override fun init() {
        super.init()
        binding.jobToolbar.setNavigationOnClickListener { viewModel.backClicked() }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobViewModel.Event.Finish -> finish()
        }
    }

    private fun finish() {
        requireActivity().onBackPressed()
    }
}