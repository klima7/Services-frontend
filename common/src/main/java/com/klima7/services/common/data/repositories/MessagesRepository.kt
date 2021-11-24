package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.sources.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.Role
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class MessagesRepository(
    private val firebase: FirebaseSource,
) {

    @ExperimentalCoroutinesApi
    suspend fun getMessages(offerId: String): Flow<List<Message>> {
        return firebase.messagesDao.getMessages(offerId)
    }

    suspend fun getLastMessage(offerId: String): Outcome<Failure, Message> {
        throw NotImplementedError()
    }

    suspend fun sendTextMessage(offerId: String, sender: Role, message: String): Outcome<Failure, None> {
        return firebase.messagesDao.sendTextMessage(offerId, sender, message)
    }

    @ExperimentalCoroutinesApi
    suspend fun sendImageMessage(offerId: String, sender: Role, imagePath: String): Outcome<Failure, None> {
        return firebase.messagesDao.sendImageMessage(offerId, sender, imagePath)
    }

    // sendMessage

}