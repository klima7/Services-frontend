package com.klima7.services.client.features.newjob.jobcreated

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobCreatedBinding
import com.klima7.services.client.databinding.FragmentJobDetailsBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobCreatedFragment: BaseFragment<FragmentJobCreatedBinding>() {

    override val layoutId = R.layout.fragment_job_created
    override val viewModel: JobCreatedViewModel by viewModel()

}
