package com.klima7.services.common.components.msgsend

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.MessagesRepository
import com.klima7.services.common.models.MessageSender

class SendImageMessageUC(
    private val messagesRepository: MessagesRepository
): BaseUC<SendImageMessageUC.Params, None>() {

    data class Params(val offerId: String, val sender: MessageSender, val imagePath: String)

    override suspend fun execute(params: Params) =
        messagesRepository.sendImageMessage(params.offerId, params.sender, params.imagePath)
}