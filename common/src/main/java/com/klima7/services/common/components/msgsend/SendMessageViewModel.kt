package com.klima7.services.common.components.msgsend

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.MessageSender
import com.klima7.services.common.platform.BaseViewModel

class SendMessageViewModel(
    private val sendTextMessageUC: SendTextMessageUC
): BaseViewModel() {

    private var sender: MessageSender? = null
    private var offerId: String? = null

    fun setSender(sender: MessageSender) {
        this.sender = sender
    }

    fun setOffer(offerId: String) {
        this.offerId = offerId
    }

    fun sendMessage(message: String) {
        val fSender = sender
        val fOfferId = offerId

        if(fSender == null || fOfferId == null) {
            return
        }

        sendTextMessageUC.start(
            viewModelScope,
            SendTextMessageUC.Params(fOfferId, fSender, message),
            { failure ->
                Log.i("Hello", "Send message failure")
            },
            {
                Log.i("Hello", "Send message success")
            }
        )
    }

}