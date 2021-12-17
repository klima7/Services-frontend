package com.klima7.services.common.messaging

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
abstract class BaseMessagingService: FirebaseMessagingService() {

    abstract val notificationManager: BaseNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager.init()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        notificationManager.handle(data)
    }

}