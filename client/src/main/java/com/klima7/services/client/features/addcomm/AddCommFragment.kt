package com.klima7.services.client.features.addcomm

import android.os.Bundle
import android.widget.Toast
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentAddCommBinding
import com.klima7.services.client.features.offers.OffersFragment
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.components.yesnodialog.YesNoDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddCommFragment: BaseFragment<FragmentAddCommBinding>() {

    companion object {
        const val ADD_COMMENT_FAILURE_DIALOG_KEY = "ADD_COMMENT_FAILURE_DIALOG_KEY"
    }

    override val layoutId = R.layout.fragment_add_comm
    override val viewModel: AddCommViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val offerId = arguments?.getString("offerId") ?: throw Error("offerId argument not supplied")
        viewModel.start(offerId)
    }

    override fun init() {
        binding.addcommToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        childFragmentManager.setFragmentResultListener(ADD_COMMENT_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(YesNoDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryAddCommentClicked()
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is AddCommViewModel.Event.ShowAddCommentFailure -> showAddCommentFailure(event.failure)
            AddCommViewModel.Event.ShowCommentAddedMessage -> showCommentAddedMessage()
        }
    }

    private fun showAddCommentFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            ADD_COMMENT_FAILURE_DIALOG_KEY,
            "Dodanie komentarza się nie powiodło.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

    private fun showCommentAddedMessage() {
        Toast.makeText(context, "Komentarz został dodany", Toast.LENGTH_SHORT).show()
    }
}