package com.klima7.services.client.features.addrating

import android.os.Bundle
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentAddRatingBinding
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.components.yesnodialog.YesNoDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddRatingFragment: BaseFragment<FragmentAddRatingBinding>() {

    companion object {
        const val ADD_RATING_FAILURE_DIALOG_KEY = "ADD_RATING_FAILURE_DIALOG_KEY"
    }

    override val layoutId = R.layout.fragment_add_rating
    override val viewModel: AddRatingViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val offerId = arguments?.getString("offerId") ?: throw Error("offerId argument not supplied")
        viewModel.start(offerId)
    }

    override fun init() {
        binding.addratingToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        childFragmentManager.setFragmentResultListener(ADD_RATING_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(YesNoDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryAddRatingClicked()
            }
        }

        viewModel.rating.observe(viewLifecycleOwner, this::updateDescription)
    }

    private fun updateDescription(rating: Float) {
        if(rating < 0) {
            return
        }
        val dtValues = resources.getStringArray(R.array.dt_scale)
        val dtSize = dtValues.size
        val index = (rating / (5.0f / dtSize)).toInt().coerceAtMost(dtSize - 1)
        val value = dtValues[index]
        binding.description = value
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is AddRatingViewModel.Event.ShowAddRatingFailure -> showAddRatingFailure(event.failure)
            AddRatingViewModel.Event.ShowRatingAddedMessage -> showRatingAddedMessage()
        }
    }

    private fun showAddRatingFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            ADD_RATING_FAILURE_DIALOG_KEY,
            requireContext().getString(R.string.addrating__add_failure_message),
            failure
        )
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

    private fun showRatingAddedMessage() {
        showToastSuccess("Ocena została dodana")
    }
}