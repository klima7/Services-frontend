package com.klima7.services.common.components.msgsend

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.MessagesRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role
import com.klima7.services.common.platform.BaseViewModel

class SendMessageViewModel(
    private val sendTextMessageUC: SendTextMessageUC,
    private val sendImageMessageUC: SendImageMessageUC,
): BaseViewModel() {

    private var sender: Role? = null
    private var offerId: String? = null

    private var lastImagePath: String? = null

    sealed class Event: BaseEvent() {
        data class ShowSendImageFailure(val failure: Failure): Event()
    }

    fun start(offerId: String, sender: Role) {
        this.offerId = offerId
        this.sender = sender
    }

    fun sendMessageClicked(message: String) {
        sendMessage(message)
    }

    fun imageSelected(imagePath: String) {
        lastImagePath = imagePath
        sendImage(imagePath)
    }

    fun retrySendImage() {
        lastImagePath?.let {
            sendImage(it)
        }
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
            { },
            { }
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
                sendEvent(Event.ShowSendImageFailure(failure))
            },
            {
                // Do nothing
            }
        )
    }

}