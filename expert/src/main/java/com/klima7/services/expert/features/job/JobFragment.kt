package com.klima7.services.expert.features.job

import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobBinding
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
}