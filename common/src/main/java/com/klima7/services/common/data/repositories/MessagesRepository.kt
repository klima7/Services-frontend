package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.MessageSender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class MessagesRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun getMessages(offerId: String): Flow<List<Message>> {
        throw NotImplementedError()
    }

    suspend fun getLastMessage(offerId: String): Outcome<Failure, Message> {
        throw NotImplementedError()
    }

    suspend fun sendTextMessage(offerId: String, message: String, sender: MessageSender): Outcome<Failure, None> {
        try {
            val data = hashMapOf(
                "author" to if(sender==MessageSender.EXPERT) "expert" else "client",
                "data" to message,
                "time" to FieldValue.serverTimestamp(),
                "type" to 0
            )
            firestore
                .collection("offers")
                .document(offerId)
                .collection("messages")
                .add(data)
                .await()
            return Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error during sendTextMessage", e)
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun sendImageMessage(offerId: String, path: String, sender: MessageSender): Outcome<Failure, None> {
        throw NotImplementedError()
    }

    // sendMessage

}