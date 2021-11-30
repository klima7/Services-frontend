package com.klima7.services.expert.features.services

import android.os.Bundle
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesBinding
import com.klima7.services.expert.features.services.multicategory.ServicesMultiCategoriesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServicesFragment: BaseLoadFragment<FragmentServicesBinding>() {

    companion object {
        const val SAVE_FAILURE_KEY = "SAVE_FAILURE_KEY"
    }

    override val layoutId = R.layout.fragment_services
    override val viewModel: ServicesViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
    }

    override fun init() {
        super.init()

        binding.servicesToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        binding.servicesSaveButton.setOnClickListener { saveButtonClicked() }

        childFragmentManager.setFragmentResultListener(SAVE_FAILURE_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retrySave()
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ServicesViewModel.Event.SetServices -> setServices(event.services)
            is ServicesViewModel.Event.ShowSaveFailure -> showSaveFailure(event.failure)
            ServicesViewModel.Event.Finish -> finish()
        }
    }

    private fun setServices(services: List<CategorizedSelectableServices>) {
        val fragment = childFragmentManager.findFragmentById(R.id.services_services) as ServicesMultiCategoriesFragment
        fragment.setServices(services)
    }

    private fun saveButtonClicked() {
        val fragment = childFragmentManager.findFragmentById(R.id.services_services) as ServicesMultiCategoriesFragment
        val selected = fragment.getSelectedServices()
        viewModel.saveClicked(selected)
    }

    private fun finish() {
        showToastSuccess("Usługi zostały zaktualizowane")
        requireActivity().finish()
    }

    private fun showSaveFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            SAVE_FAILURE_KEY,
            "Zmiana usług się nie powiodła.", failure)
        dialog.show(childFragmentManager, SAVE_FAILURE_KEY)
    }
}
