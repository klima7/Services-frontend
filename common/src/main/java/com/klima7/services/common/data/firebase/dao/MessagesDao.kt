package com.klima7.services.common.data.firebase.dao

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.converters.toDomain
import com.klima7.services.common.data.firebase.entities.MessageEntity
import com.klima7.services.common.data.firebase.utils.toDomain
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.Role
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.*
import kotlin.coroutines.resume

class MessagesDao(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val functions: FirebaseFunctions,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getMessages(offerId: String): Flow<List<Message>> = callbackFlow {

        val query = firestore
            .collection("offers")
            .document(offerId)
            .collection("messages")
            .orderBy("time", Query.Direction.ASCENDING)

        val subscription = query.addSnapshotListener(MetadataChanges.INCLUDE) { querySnap, _ ->
            if(querySnap != null) {
                val messages = mutableListOf<Message>()
                for(documentSnap in querySnap.documents) {
                    val entity = documentSnap.toObject(MessageEntity::class.java)
                    val message = entity?.toDomain(documentSnap.metadata.hasPendingWrites())
                    if(message != null) {
                        messages.add(message)
                    }
                }
                trySend(messages.toList())
            }
        }

        awaitClose {
            subscription.remove()
        }
    }

    suspend fun sendTextMessage(offerId: String, sender: Role, message: String): Outcome<Failure, None> {
        try {
            val data = hashMapOf(
                "author" to if(sender==Role.EXPERT) "expert" else "client",
                "message" to message,
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
            Timber.e(e, "Error during sendTextMessage")
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun sendImageMessage(offerId: String, @Suppress("UNUSED_PARAMETER") sender: Role, imagePath: String):
            Outcome<Failure, None> = suspendCancellableCoroutine { continuation ->

        val uid = auth.currentUser?.uid
        if(uid == null) {
            continuation.resume(Outcome.Failure(Failure.InternetFailure))
        }

        val uuid = UUID.randomUUID().toString()

        val task = storage.reference
            .child("chats_images")
            .child(offerId)
            .child(uid!!)
            .child("${uuid}.png")
            .putFile(Uri.parse(imagePath))
            .addOnSuccessListener {
                continuation.resume(Outcome.Success(None()))
            }
            .addOnFailureListener { e ->
                continuation.resume(Outcome.Failure(e.toDomain()))
            }
            .addOnCanceledListener {
                continuation.resume(Outcome.Failure(Failure.InternetFailure))
            }

        continuation.invokeOnCancellation {
            task.cancel()
        }
    }

    suspend fun setLastReadTime(role: Role, offerId: String, date: Date): Outcome<Failure, None> {
        return try {
            val data: Map<String, Any>
            if(role == Role.CLIENT) {
                data = hashMapOf<String, Any>(
                    "clientReadTime" to Timestamp(date),
                )
            }
            else {
                data = hashMapOf<String, Any>(
                    "expertReadTime" to Timestamp(date),
                )
            }
            firestore
                .collection("offers")
                .document(offerId)
                .update(data)
            Outcome.Success(None())
        } catch(e: Exception) {
            Timber.e(e, "Error during setLastReadTime")
            Outcome.Failure(e.toDomain())
        }
    }

}
