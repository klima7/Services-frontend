package com.klima7.services.client.messaging

import android.app.Service
import android.content.Intent
import com.klima7.services.client.R
import com.klima7.services.client.features.splash.SplashActivity
import com.klima7.services.common.messaging.BaseNotificationManager
import timber.log.Timber

class NotificationManager(
    private val service: Service,
): BaseNotificationManager(service) {

    override val channelId = "services clients"
    override val largeIcon = R.drawable.icon_notification_large
    override val smallIcon = R.drawable.icon_notification_small

    override fun getSplashIntent(): Intent {
        return Intent(service.applicationContext, SplashActivity::class.java)
    }

    override fun handle(notificationData: Map<String, String>): Boolean {
        if(super.handle(notificationData)) {
            return true
        }
        val type = notificationData["type"] ?: return false
        return when(type) {
            "new-offer" -> {
                handleNewOfferMessage(notificationData)
                true
            }
            else -> false
        }
    }

    private fun handleNewOfferMessage(notificationData: Map<String, String>) {
        Timber.tag("Hello").i("Handling new offer message")
        val senderName = notificationData["sender"] ?: return
        val serviceName = notificationData["service"] ?: return
        val offerId = notificationData["offerId"] ?: return

        val title = service.resources.getString(
            com.klima7.services.common.R.string.notification__new_offer_title, senderName)
        val body = service.resources.getString(
            com.klima7.services.common.R.string.notification__new_offer_body, serviceName)
        showNotification(offerId, title, body, offerId)
    }
}