package com.klima7.services.client.messaging

import android.annotation.SuppressLint
import com.klima7.services.common.notifications.BaseMessagingService


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MessagingService: BaseMessagingService() {

    override val notificationHandler = NotificationHandler(this)

}