package com.klima7.services.expert.features.job

import android.util.Log
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobFragment: BaseFragment<FragmentJobBinding>() {

    override val layoutId = R.layout.fragment_job
    override val viewModel: JobViewModel by viewModel()

    override fun init() {
        super.init()

        val args = arguments
        Log.i("Hello", "Arguments: $args")
        Log.i("Hello", "Arguments: ${args?.getString("jobId")}")
        binding.jobText.text = arguments?.getString("jobId")
    }
}