package com.klima7.services.expert.messaging

import android.annotation.SuppressLint
import com.klima7.services.common.notifications.BaseMessagingService


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MessagingService: BaseMessagingService() {

    override val notificationManager = NotificationManager(this)

}