package com.klima7.services.common.components.msgsend

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.klima7.services.common.R
import com.klima7.services.common.components.views.SendMessageBarView
import com.klima7.services.common.databinding.FragmentSendMessageBinding
import com.klima7.services.common.models.MessageSender
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

import android.content.Intent
import android.provider.MediaStore


class SendMessageFragment: BaseFragment<FragmentSendMessageBinding>(), SendMessageBarView.Listener {

    override val layoutId = R.layout.fragment_send_message
    override val viewModel: SendMessageViewModel by viewModel()

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        this.onImagePickerFinished(result)
    }

    override fun init() {
        super.init()
        binding.msgsendSendMessageBar.setListener(this)
    }

    fun initialize(sender: MessageSender, offerId: String) {
        setSender(sender)
        setOffer(offerId)
    }

    fun setSender(sender: MessageSender) {
        viewModel.setSender(sender)
    }

    fun setOffer(offerId: String) {
        viewModel.setOffer(offerId)
    }

    override fun onSendMessageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Send message")
        viewModel.sendMessageClicked(smb.text)
        smb.clear()
    }

    override fun onSelectImageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Select image")
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
}