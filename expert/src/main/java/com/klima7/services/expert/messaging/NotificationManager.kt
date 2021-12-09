package com.klima7.services.expert.messaging

import android.app.Service
import com.klima7.services.common.notifications.BaseNotificationManager
import com.klima7.services.expert.R

class NotificationManager(
    private val service: Service,
): BaseNotificationManager(service) {

    override val channelId = "services expert";
    override val largeIcon = R.drawable.icon_notification_large
    override val smallIcon = R.drawable.icon_notification_small

}