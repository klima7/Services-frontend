package com.klima7.services.expert.features.job

import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobFragment: BaseFragment<FragmentSettingsBinding>() {

    override val layoutId = R.layout.fragment_settings
    override val viewModel: JobViewModel by viewModel()

}