package com.klima7.services.common.components.msgsend

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.MessageSender
import com.klima7.services.common.platform.BaseViewModel

class SendMessageViewModel(
    private val sendTextMessageUC: SendTextMessageUC,
    private val sendImageMessageUC: SendImageMessageUC,
): BaseViewModel() {

    private var sender: MessageSender? = null
    private var offerId: String? = null

    fun setSender(sender: MessageSender) {
        this.sender = sender
    }

    fun setOffer(offerId: String) {
        this.offerId = offerId
    }

    fun sendMessageClicked(message: String) {
        sendMessage(message)
    }

    fun imageSelected(imagePath: String) {
        sendImage(imagePath)
    }

    private fun sendMessage(message: String) {
        val fSender = sender
        val fOfferId = offerId

        if(fSender == null || fOfferId == null) {
            return
        }

        sendTextMessageUC.start(
            viewModelScope,
            SendTextMessageUC.Params(fOfferId, fSender, message),
            { failure ->
                Log.i("Hello", "Send message failure: $failure")
            },
            {
                Log.i("Hello", "Send message success")
            }
        )
    }

    private fun sendImage(imagePath: String) {
        val fSender = sender
        val fOfferId = offerId

        if(fSender == null || fOfferId == null) {
            return
        }

        sendImageMessageUC.start(
            viewModelScope,
            SendImageMessageUC.Params(fOfferId, fSender, imagePath),
            { failure ->
                Log.i("Hello", "Send image failure: $failure")
            },
            {
                Log.i("Hello", "Send image success")
            }
        )
    }

}