package com.klima7.services.common.components.msgsend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.klima7.services.common.R
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.components.views.SendMessageBarView
import com.klima7.services.common.databinding.FragmentSendMessageBinding
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SendMessageFragment:
    BaseFragment<FragmentSendMessageBinding>(), SendMessageBarView.Listener {

    override val layoutId = R.layout.fragment_send_message
    override val viewModel: SendMessageViewModel by viewModel()

    companion object {
        const val SEND_IMAGE_FAILURE_DIALOG_KEY = "SEND_IMAGE_FAILURE_DIALOG_KEY"

        fun newInstance(offerId: String, role: Role) =
            SendMessageFragment().apply {
                arguments = bundleOf(
                    "offerId" to offerId,
                    "role" to role
                )
            }
    }

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        this.onImagePickerFinished(result)
    }

    override fun init() {
        super.init()

        binding.msgsendSendMessageBar.setListener(this)

        childFragmentManager.setFragmentResultListener(SEND_IMAGE_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retrySendImage()
            }
        }
    }

    override fun onFirstCreation() {
        super.onFirstCreation()

        val offerId = arguments?.getString("offerId") ?: throw Error("offerId not supplied")
        val role = arguments?.getSerializable("role") as? Role  ?: throw Error("role not supplied")

        viewModel.start(offerId, role)
    }

    override fun onSendMessageClicked(smb: SendMessageBarView) {
        viewModel.sendMessageClicked(smb.text)
        smb.clear()
    }

    override fun onSelectImageClicked(smb: SendMessageBarView) {
        pickImage()
    }

    private fun pickImage() {
        val pickImageIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        imagePickerLauncher.launch(pickImageIntent)
    }

    private fun onImagePickerFinished(activityResult: ActivityResult) {
        if(activityResult.resultCode == Activity.RESULT_OK) {
            val imagePath = activityResult.data?.data?.toString() ?: return
            viewModel.imageSelected(imagePath)
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is SendMessageViewModel.Event.ShowSendImageFailure -> showSendImageFailure(event.failure)
        }
    }

    private fun showSendImageFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            SEND_IMAGE_FAILURE_DIALOG_KEY,
            "Wysyłanie obrazu nieudane.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

}