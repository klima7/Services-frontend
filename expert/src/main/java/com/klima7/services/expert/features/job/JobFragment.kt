package com.klima7.services.expert.features.job

import android.util.Log
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Error

class JobFragment: BaseLoadFragment<FragmentJobBinding>() {

    override val layoutId = R.layout.fragment_job
    override val viewModel: JobViewModel by viewModel()

    private lateinit var jobId: String

    override fun init() {
        super.init()

        val args = arguments
        Log.i("Hello", "Arguments: $args")
        Log.i("Hello", "Arguments: ${args?.getString("jobId")}")


        viewModel.job.observe(viewLifecycleOwner, { job ->
            if(job != null) {
                binding.jobView.setJob(job)
            }
        })
    }

    override fun onFirstCreation() {
        super.onFirstCreation()

        jobId = arguments?.getString("jobId") ?: throw Error("JobId argument not supplied")
        Log.i("Hello", "JobId=$jobId")
        viewModel.start(jobId)
    }
}