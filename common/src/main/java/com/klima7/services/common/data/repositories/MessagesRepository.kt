package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.Role
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import java.util.*

class MessagesRepository(
    private val firebase: FirebaseSource,
) {

    @ExperimentalCoroutinesApi
    suspend fun getMessages(offerId: String): Flow<List<Message>> {
        return firebase.messagesDao.getMessages(offerId)
    }

    suspend fun sendTextMessage(offerId: String, sender: Role, message: String): Outcome<Failure, None> {
        return firebase.messagesDao.sendTextMessage(offerId, sender, message)
    }

    @ExperimentalCoroutinesApi
    suspend fun sendImageMessage(offerId: String, sender: Role, imagePath: String): Outcome<Failure, None> {
        return firebase.messagesDao.sendImageMessage(offerId, sender, imagePath)
    }

    suspend fun setLastReadTime(role: Role, offerId: String, date: Date): Outcome<Failure, None> {
        return firebase.messagesDao.setLastReadTime(role, offerId, date)
    }

}