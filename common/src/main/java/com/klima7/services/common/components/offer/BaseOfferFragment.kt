package com.klima7.services.common.components.offer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.components.rating.RatingActivity
import com.klima7.services.common.components.yesnodialog.YesNoDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.ui.OfferStatusDescription

abstract class BaseOfferFragment<DB: ViewDataBinding>: BaseFragment<DB>() {

    companion object {
        const val OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY = "OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY"
        const val OFFER_STATUS_CHANGE_FAILURE_DIALOG_KEY = "OFFER_STATUS_CHANGE_FAILURE_DIALOG_KEY"
    }

    abstract override val viewModel: BaseOfferViewModel

    override fun init() {
        super.init()

        childFragmentManager.setFragmentResultListener(OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY,
            viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(YesNoDialogFragment.BUNDLE_KEY)
            if(result == YesNoDialogFragment.Result.YES) {
                viewModel.changeOfferStatusConfirmed()
            }
        }

        childFragmentManager.setFragmentResultListener(OFFER_STATUS_CHANGE_FAILURE_DIALOG_KEY,
            viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryChangeOfferStatus()
            }
        }

    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is BaseOfferViewModel.Event.ShowOfferStatusChangeEnsureDialog ->
                showOfferStatusChangeEnsureDialog(event.newOfferStatus)
            is BaseOfferViewModel.Event.Call -> call(event.phoneNumber)
            is BaseOfferViewModel.Event.ShowCommentScreen -> showCommentScreen(event.ratingId)
            is BaseOfferViewModel.Event.ShowOfferStatusChangeFailureDialog ->
                showOfferStatusChangeFailureDialog(event.failure)
        }
    }

    private fun showOfferStatusChangeEnsureDialog(newOfferStatus: OfferStatus) {
        val statusName = OfferStatusDescription.get(newOfferStatus).getText(requireContext())
        val dialog = YesNoDialogFragment.create(
            OFFER_STATUS_CHANGE_ENSURE_DIALOG_KEY,
            "Czy na pewno chcesz zmienić status oferty na \"$statusName\"")
        dialog.show(childFragmentManager, "YesNoDialogFragment")
    }

    private fun call(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    private fun showCommentScreen(ratingId: String) {
        val intent = Intent(requireContext(), RatingActivity::class.java)
        val bundle = bundleOf(
            "ratingId" to ratingId,
            "exit" to "slideDown",
        )
        intent.putExtras(bundle)
        startActivity(intent)
        Animatoo.animateSlideUp(requireActivity())
    }

    private fun showOfferStatusChangeFailureDialog(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            OFFER_STATUS_CHANGE_FAILURE_DIALOG_KEY,
            "Zmiana statusu oferty się nie powiodła.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }
}
