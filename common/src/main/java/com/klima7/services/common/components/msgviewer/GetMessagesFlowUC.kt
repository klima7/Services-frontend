package com.klima7.services.common.components.msgviewer

import android.util.Log
import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.MessagesRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.util.*

class GetMessagesFlowUC(
    private val messagesRepository: MessagesRepository
): BaseUC<GetMessagesFlowUC.Params, Flow<List<Message>>>() {

    data class Params(val role: Role, val offerId: String)

    private lateinit var offerId: String
    private lateinit var role: Role

    private var latestMessageTime: Date? = null

    override suspend fun execute(params: Params): Outcome<Failure, Flow<List<Message>>> {
        this.offerId = params.offerId
        this.role = params.role
        return Outcome.Success(messagesRepository.getMessages(params.offerId).onEach(this::handleLastReadMessage))
    }

    private suspend fun handleLastReadMessage(messages: List<Message>) {
        val cLatestMessageTime = latestMessageTime?.time
        val latestMessage = getLatestMessage(messages)
        if(cLatestMessageTime == null || latestMessage.sendTime.time >= cLatestMessageTime) {
            messagesRepository.setLastReadTime(role, offerId, latestMessage.sendTime)
            latestMessageTime = latestMessage.sendTime
        }
    }

    private fun getLatestMessage(messages: List<Message>): Message {
        val otherUserMessages =
            messages.filter { message -> message.author != role.toMessageAuthor() }
        val otherUserMessagesSorted =
            otherUserMessages.sortedByDescending { message -> message.sendTime }
        return otherUserMessagesSorted[0]
    }
}
