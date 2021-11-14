package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.Outcome
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import kotlinx.coroutines.flow.Flow

class MessagesRepository {

    suspend fun getMessages(offerId: String): Flow<List<Message>> {
        throw NotImplementedError()
    }

    suspend fun getLastMessage(offerId: String): Outcome<Failure, Message> {
        throw NotImplementedError()
    }

    // sendMessage

}