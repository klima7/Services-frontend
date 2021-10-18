package com.klima7.services.expert.features.services

import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.faildialog.FailureDialogFragment
import com.klima7.services.common.ui.loadable.LoadableFragment
import com.klima7.services.common.ui.utils.showShortToast
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesBinding
import com.klima7.services.expert.features.area.WorkingAreaContentFragment
import com.klima7.services.expert.features.services.multicategory.ServicesMultiCategoriesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServicesContentFragment: LoadableFragment<FragmentServicesBinding>() {

    companion object {
        const val SAVE_FAILURE_KEY = "SAVE_FAILURE_KEY"
    }

    override val layoutId = R.layout.fragment_services
    override val viewModel: ServicesContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
    }

    override fun init() {
        super.init()
        binding.servicesSaveButton.setOnClickListener { saveButtonClicked() }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ServicesContentViewModel.Event.SetServices -> setServices(event.services)
            is ServicesContentViewModel.Event.ShowSaveFailure -> showSaveFailure(event.failure)
            ServicesContentViewModel.Event.Finish -> finish()
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
        showShortToast("Usługi zostały zaktualizowane")
        requireActivity().finish()
    }

    private fun showSaveFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            SAVE_FAILURE_KEY,
            "Zmiana usług się nie powiodła.", failure)
        dialog.show(childFragmentManager, SAVE_FAILURE_KEY)
    }
}
