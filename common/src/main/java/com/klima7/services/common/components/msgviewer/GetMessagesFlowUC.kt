package com.klima7.services.common.components.msgviewer

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

    private var currentLast: Date? = null

    override suspend fun execute(params: Params): Outcome<Failure, Flow<List<Message>>> {
        this.offerId = params.offerId
        this.role = params.role
        return Outcome.Success(messagesRepository.getMessages(params.offerId).onEach(this::updateLastReadMessage))
    }

    private suspend fun updateLastReadMessage(messages: List<Message>) {
        // Input
        val cCurrentLast = currentLast?.time
        val last = getLastMessageTime(messages)

        // Output
        var timeToUpdate: Date? = null

        // Logic
        if(last == null) {
            if(cCurrentLast == null) {
                timeToUpdate = Date()
            }
        }
        else if(cCurrentLast == null || last.time >= cCurrentLast) {
            timeToUpdate = last
        }

        // Perform operation
        if(timeToUpdate != null) {
            context
            messagesRepository.setLastReadTime(role, offerId, timeToUpdate)
            currentLast = last
        }
    }

    private fun getLastMessageTime(messages: List<Message>): Date? {
        val processedMessages = messages
            .sortedByDescending { message -> message.sendTime }
        return if(processedMessages.isNotEmpty()) processedMessages[0].sendTime else null
    }
}
