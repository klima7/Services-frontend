package com.klima7.services.client.features.newjob.jobdetails

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobDetailsBinding
import com.klima7.services.client.databinding.FragmentServiceBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobDetailsFragment: BaseFragment<FragmentJobDetailsBinding>() {

    override val layoutId = R.layout.fragment_job_details
    override val viewModel: JobDetailsViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
    }

    override fun init() {
        super.init()
        binding.serviceToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

}