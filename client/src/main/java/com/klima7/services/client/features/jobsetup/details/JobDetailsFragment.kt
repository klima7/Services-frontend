package com.klima7.services.client.features.jobsetup.details

import android.os.Bundle
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobDetailsBinding
import com.klima7.services.client.features.jobsetup.JobSetupViewModel
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobDetailsFragment: BaseFragment<FragmentJobDetailsBinding>() {

    companion object {
        const val CREATE_JOB_FAILURE_DIALOG_KEY = "CREATE_JOB_FAILURE_DIALOG_KEY"
    }

    override val layoutId = R.layout.fragment_job_details
    override val viewModel: JobDetailsViewModel by viewModel()

    private val parentViewModel: JobSetupViewModel by lazy {
        requireParentFragment().requireParentFragment().getViewModel()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        val service = parentViewModel.service.value!!
        val location = parentViewModel.location.value!!
        viewModel.start(service, location)
    }

    override fun init() {
        super.init()

        childFragmentManager.setFragmentResultListener(CREATE_JOB_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryCreateJobClicked()
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobDetailsViewModel.Event.ShowJobCreatedScreen -> showJobCreatedScreen()
            is JobDetailsViewModel.Event.ShowJobCreateFailure -> showJobCreationFailure(event.failure)
        }
    }

    private fun showJobCreatedScreen() {
        parentViewModel.showCreatedScreen()
    }

    private fun showJobCreationFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            CREATE_JOB_FAILURE_DIALOG_KEY,
            "Utworzenie oferty się nie powiodło", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

}