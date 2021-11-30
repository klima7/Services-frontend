package com.klima7.services.common.components.offer

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.klima7.services.common.components.yesnodialog.YesNoDialogFragment
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseOfferFragment<DB: ViewDataBinding>: BaseFragment<DB>() {

    companion object {
        const val OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY = "OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY"
    }

    abstract override val viewModel: BaseOfferViewModel

    override fun init() {
        super.init()

        childFragmentManager.setFragmentResultListener(OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(YesNoDialogFragment.BUNDLE_KEY)
            if(result == YesNoDialogFragment.Result.YES) {
                viewModel.changeOfferStatusConfirmed()
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is BaseOfferViewModel.Event.ShowOfferStatusChangeEnsureDialog ->
                showOfferStatusChangeEnsureDialog(event.newOfferStatus)
        }
    }

    private fun showOfferStatusChangeEnsureDialog(newOfferStatus: OfferStatus) {
        val dialog = YesNoDialogFragment.create(
            OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY,
            "Czy na pewno chcesz zmienić status oferty na $newOfferStatus")
        dialog.show(childFragmentManager, "YesNoDialogFragment")
    }
}
