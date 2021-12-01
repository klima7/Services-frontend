package com.klima7.services.common.components.delete

import android.os.Bundle
import com.klima7.services.common.R
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.databinding.FragmentDeleteBinding
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel


abstract class BaseDeleteFragment(
    private val titleRes: Int,
    private val textRes: Int,
): BaseFragment<FragmentDeleteBinding>() {

    override val layoutId = R.layout.fragment_delete
    abstract override val viewModel: BaseDeleteViewModel

    companion object {
        const val DELETE_FAILURE_DIALOG_KEY = "DELETE_FAILURE_DIALOG_KEY"
    }

    override fun init() {
        super.init()

        childFragmentManager.setFragmentResultListener(DELETE_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryDeleteClicked()
            }
        }

        viewModel.setConfirmText(resources.getString(R.string.confirm))
        binding.deleteToolbar.apply {
            title = resources.getString(titleRes)
            setNavigationIcon(R.drawable.icon_arrow_back)
            setNavigationOnClickListener { viewModel.backClicked() }
        }
        binding.deleteText.text = resources.getString(textRes)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            BaseDeleteViewModel.Event.ShowSplashScreen -> showSplashScreen()
            BaseDeleteViewModel.Event.Finish -> finish()
            is BaseDeleteViewModel.Event.ShowDeleteFailure -> showDeleteFailure(event.failure)
        }
    }

    private fun showDeleteFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            DELETE_FAILURE_DIALOG_KEY,
            "Usunięcie konta się nie powiodło.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

    private fun finish() {
        requireActivity().finish()
    }

    protected abstract fun showSplashScreen()

}
