package com.klima7.services.common.notifications

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


abstract class BaseMessagingService: FirebaseMessagingService() {

    abstract val notificationHandler: BaseNotificationsHandler

    override fun onCreate() {
        super.onCreate()
        notificationHandler.init()
    }

    override fun getStartCommandIntent(p0: Intent): Intent {
        Log.i("Hello", "getStartCommandIntent")
        return super.getStartCommandIntent(p0)
    }

    override fun handleIntent(intent: Intent) {
        Log.i("Hello", "handleIntent")
        super.handleIntent(intent)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i("Hello", "Message read")
        val data = remoteMessage.data
        notificationHandler.handle(data)
    }

    override fun onDeletedMessages() {
        Log.i("Hello", "onDeletedMessages")
        super.onDeletedMessages()
    }

    override fun onMessageSent(p0: String) {
        Log.i("Hello", "onMessageSent")
        super.onMessageSent(p0)
    }

    override fun onSendError(p0: String, p1: Exception) {
        Log.i("Hello", "onSendError")
        super.onSendError(p0, p1)
    }

    override fun onNewToken(p0: String) {
        Log.i("Hello", "onNewToken")
        super.onNewToken(p0)
    }

}