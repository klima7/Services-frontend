package com.klima7.services.client.messaging

import android.annotation.SuppressLint
import com.klima7.services.common.messaging.BaseMessagingService


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MessagingService: BaseMessagingService() {

    override val notificationManager = NotificationManager(this)

}