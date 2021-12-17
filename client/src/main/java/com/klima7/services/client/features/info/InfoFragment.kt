package com.klima7.services.client.features.info

import android.os.Bundle
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentInfoBinding
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment: BaseFragment<FragmentInfoBinding>() {

    override val layoutId = R.layout.fragment_info
    override val viewModel: InfoViewModel by viewModel()

    companion object {
        const val SAVE_INFO_FAILURE_KEY = "SAVE_INFO_FAILURE_KEY"
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
    }

    override fun init() {
        super.init()

        binding.infoToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        childFragmentManager.setFragmentResultListener(SAVE_INFO_FAILURE_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retrySaveClicked()
            }
        }

        viewModel.nameError.observe(viewLifecycleOwner) { nameError ->
            binding.infoName.error = when(nameError) {
                null -> null
                InfoFormErrors.NameError.NotProvided ->
                    requireContext().getString(R.string.info__field_required_error)
            }
        }

        viewModel.phoneError.observe(viewLifecycleOwner) { phoneError ->
            binding.infoPhone.error = when(phoneError) {
                null -> null
                InfoFormErrors.PhoneError.TooShort ->
                    requireContext().getString(R.string.info__phone_too_short_error)
            }
        }

    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            InfoViewModel.Event.FinishInfo -> finishInfo()
            is InfoViewModel.Event.ShowSaveFailure -> showSaveError(event.failure)
        }
    }

    private fun finishInfo() {
        showToastSuccess(requireContext().getString(R.string.info__updated_successfully_message))
        requireActivity().finish()
    }

    private fun showSaveError(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            SAVE_INFO_FAILURE_KEY,
            requireContext().getString(R.string.info__update_failure_message),
            failure
        )
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

}
