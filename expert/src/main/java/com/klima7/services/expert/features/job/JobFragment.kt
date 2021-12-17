package com.klima7.services.expert.features.job

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentJobBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobFragment: BaseFragment<FragmentJobBinding>() {

    companion object {
        const val ACCEPT_FAILURE_DIALOG_KEY = "ACCEPT_FAILURE_DIALOG_KEY"
        const val REJECT_FAILURE_DIALOG_KEY = "REJECT_DIALOG_KEY"

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

        childFragmentManager.setFragmentResultListener(ACCEPT_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryAccept()
            }
        }

        childFragmentManager.setFragmentResultListener(REJECT_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryReject()
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobViewModel.Event.FinishWithNoop -> finishWithResult(RESULT_STATE_NOOP)
            JobViewModel.Event.FinishWithAccept -> finishWithResult(RESULT_STATE_ACCEPT)
            JobViewModel.Event.FinishWithReject -> finishWithResult(RESULT_STATE_REJECT)
            is JobViewModel.Event.ShowAcceptFailureDialog -> showAcceptFailureDialog(event.failure)
            is JobViewModel.Event.ShowRejectFailureDialog -> showRejectFailureDialog(event.failure)
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

    private fun showAcceptFailureDialog(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            ACCEPT_FAILURE_DIALOG_KEY,
            requireContext().getString(R.string.job__accept_failure_message),
            failure
        )
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

    private fun showRejectFailureDialog(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            REJECT_FAILURE_DIALOG_KEY,
            requireContext().getString(R.string.job__reject_failure_message),
            failure
        )
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

}