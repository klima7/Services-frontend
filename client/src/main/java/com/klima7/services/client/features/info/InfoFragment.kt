package com.klima7.services.client.features.info

import android.os.Bundle
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentInfoBinding
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment: BaseLoadFragment<FragmentInfoBinding>() {

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

        childFragmentManager.setFragmentResultListener(SAVE_INFO_FAILURE_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retrySaveClicked()
            }
        }

        viewModel.nameError.observe(viewLifecycleOwner) { nameError ->
            binding.infoName.error = when(nameError) {
                null -> null
                InfoFormErrors.NameError.NotProvided -> "To pole jest wymagane"
            }
        }

        viewModel.phoneError.observe(viewLifecycleOwner) { phoneError ->
            binding.infoPhone.error = when(phoneError) {
                null -> null
                InfoFormErrors.PhoneError.TooShort -> "Numer za krótki"
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
        showShortToast("Profil został zaktualizowany")
        requireActivity().finish()
    }

    private fun showSaveError(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            SAVE_INFO_FAILURE_KEY,
            "Zmiana profilu się nie powiodła.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

}
