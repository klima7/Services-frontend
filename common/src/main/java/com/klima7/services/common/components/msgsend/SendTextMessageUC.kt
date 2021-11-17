package com.klima7.services.common.components.msgsend

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.MessagesRepository
import com.klima7.services.common.models.Role

class SendTextMessageUC(
    private val messagesRepository: MessagesRepository
): BaseUC<SendTextMessageUC.Params, None>() {

    data class Params(val offerId: String, val sender: Role, val message: String)

    override suspend fun execute(params: Params) =
        messagesRepository.sendTextMessage(params.offerId, params.sender, params.message)
}