package com.klima7.services.expert.messaging

import android.app.Service
import com.klima7.services.common.notifications.BaseNotificationsHandler
import com.klima7.services.expert.R

class NotificationHandler(
    private val service: Service,
): BaseNotificationsHandler(service) {

    override val channelId = "services expert";
    override val largeIcon = R.drawable.icon_notification_large
    override val smallIcon = R.drawable.icon_notification_small

    override fun handle(notificationData: Map<String, String>): Boolean {
        if(super.handle(notificationData)) {
            return true
        }
        showNotification(1, "Title", "Body")
        return true
    }

}