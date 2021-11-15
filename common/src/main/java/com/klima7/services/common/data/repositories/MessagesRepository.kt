package com.klima7.services.common.data.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.MessageSender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*

class MessagesRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) {

    suspend fun getMessages(offerId: String): Flow<List<Message>> {
        throw NotImplementedError()
    }

    suspend fun getLastMessage(offerId: String): Outcome<Failure, Message> {
        throw NotImplementedError()
    }

    suspend fun sendTextMessage(offerId: String, sender: MessageSender, message: String): Outcome<Failure, None> {
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

    suspend fun sendImageMessage(offerId: String, sender: MessageSender, imagePath: String): Outcome<Failure, None> {
        return try {
            val uid = auth.currentUser?.uid ?: return Outcome.Failure(Failure.PermissionFailure)
            val uuid = UUID.randomUUID().toString()
            storage.reference
                .child("chats_images")
                .child(offerId)
                .child(uid)
                .child("${uuid}.png")
                .putFile(Uri.parse(imagePath))
                .await()
            Outcome.Success(None())
        } catch(e: Exception) {
            Log.e("Hello", "Error while sendImageMessage", e)
            Outcome.Failure(e.toDomain())
        }
    }

    // sendMessage

}