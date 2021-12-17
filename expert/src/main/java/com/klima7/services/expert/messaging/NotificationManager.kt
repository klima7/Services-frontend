package com.klima7.services.expert.messaging

import android.app.Service
import android.content.Intent
import com.klima7.services.common.messaging.BaseNotificationManager
import com.klima7.services.expert.R
import com.klima7.services.expert.features.splash.SplashActivity

class NotificationManager(
    private val service: Service,
): BaseNotificationManager(service) {

    override val channelId = "services expert"
    override val largeIcon = R.drawable.icon_notification_large
    override val smallIcon = R.drawable.icon_notification_small

    override fun handle(notificationData: Map<String, String>): Boolean {
        if(super.handle(notificationData)) {
            return true
        }
        val type = notificationData["type"] ?: return false
        return when(type) {
            "rating-added" -> {
                handleRatingAddedMessage(notificationData)
                true
            }
            else -> false
        }
    }

    private fun handleRatingAddedMessage(notificationData: Map<String, String>) {
        val senderName = notificationData["sender"] ?: return
        val offerId = notificationData["offerId"] ?: return
//        val ratingId = notificationData["ratingId"] ?: return
        val ratingString = notificationData["rating"] ?: return
        var ratingNumber: Double
        try {
            ratingNumber = ratingString.toDouble()
        } catch (e: NumberFormatException) {
            return
        }
        val formattedRating = String.format("%.2f", ratingNumber)
        val title = service.resources.getString(
            com.klima7.services.common.R.string.notification__rating_added_title, senderName)
        val body = service.resources.getString(
            com.klima7.services.common.R.string.notification__rating_added_body, formattedRating)
        showNotification(offerId, title, body)
    }

    override fun getSplashIntent(): Intent {
        return Intent(service.applicationContext, SplashActivity::class.java)
    }
}