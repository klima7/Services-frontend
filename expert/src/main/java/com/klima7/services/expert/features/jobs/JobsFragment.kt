package com.klima7.services.expert.features.jobs

import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobsBinding
import com.klima7.services.expert.features.settings.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobsFragment: BaseFragment<FragmentJobsBinding>() {

    override val layoutId = R.layout.fragment_jobs
    override val viewModel: JobsViewModel by viewModel()

}