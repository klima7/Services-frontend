package com.klima7.services.expert.features.job

import android.app.Activity
import android.content.Intent
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobFragment: BaseFragment<FragmentJobBinding>() {

    companion object {
        const  val RESULT_JOB_KEY = "job"
        const  val RESULT_STATE_KEY = "state"
        const  val RESULT_STATE_ACCEPT = "accept"
        const  val RESULT_STATE_REJECT = "reject"
        const  val RESULT_STATE_NOOP = "noop"
    }

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
        setResult(RESULT_STATE_NOOP)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobViewModel.Event.FinishWithNoop -> finishWithResult(RESULT_STATE_NOOP)
            JobViewModel.Event.FinishWithAccept -> finishWithResult(RESULT_STATE_ACCEPT)
            JobViewModel.Event.FinishWithReject -> finishWithResult(RESULT_STATE_REJECT)
        }
    }

    private fun finishWithResult(result: String) {
        setResult(result)
        requireActivity().finish()
    }

    private fun setResult(result: String) {
        val intent = Intent()
        intent.putExtra(RESULT_STATE_KEY, result)
        intent.putExtra(RESULT_JOB_KEY, viewModel.jobId)
        requireActivity().setResult(Activity.RESULT_OK, intent)
    }

}