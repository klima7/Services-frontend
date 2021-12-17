package com.klima7.services.common.messaging

import android.app.*
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.klima7.services.common.R
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.ui.OfferStatusDescription


abstract class BaseNotificationManager(
    private val service: Service,
) {

    private val auth = Firebase.auth

    companion object {
        const val NOTIFICATION_PRIORITY = NotificationCompat.PRIORITY_MAX
    }

    abstract val channelId: String
    abstract val smallIcon: Int
    abstract val largeIcon: Int

    fun init() {
        createChannel()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Services"
            val descriptionText = "All services app notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                service.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    open fun handle(notificationData: Map<String, String>): Boolean {
        val uid = notificationData["uid"]
        if(uid == null || uid != auth.uid) {
            return true
        }

        val type = notificationData["type"] ?: return false
        return when(type) {
            "text-message" -> {
                handleTextMessage(notificationData)
                true
            }
            "image-message" -> {
                handleImageMessage(notificationData)
                true
            }
            "offer-read" -> {
                handleReadMessage(notificationData)
                true
            }
            "status-change" -> {
                handleStatusChangeMessage(notificationData)
                true
            }
            else -> false
        }
    }

    private fun handleTextMessage(notificationData: Map<String, String>) {
        val senderName = notificationData["sender"] ?: return
        val message = notificationData["message"] ?: return
        val offerId = notificationData["offerId"] ?: return
        val title = service.resources.getString(R.string.notification__text_message_from, senderName)
        showNotification(offerId, title, message, offerId)
    }

    private fun handleImageMessage(notificationData: Map<String, String>) {
        val senderName = notificationData["sender"] ?: return
        val offerId = notificationData["offerId"] ?: return
        val title = service.resources.getString(R.string.notification__text_message_from, senderName)
        val body = service.resources.getString(R.string.notification__image_message_sent)
        showNotification(offerId, title, body, offerId)
    }

    private fun handleStatusChangeMessage(notificationData: Map<String, String>) {
        val senderName = notificationData["sender"] ?: return
        val offerId = notificationData["offerId"] ?: return
        val newStatus = notificationData["newStatus"] ?: return
        val title = service.resources.getString(R.string.notification__status_change_title, senderName)
        val status = when(newStatus) {
            "0" -> OfferStatus.NEW
            "1" -> OfferStatus.CANCELLED
            "2" -> OfferStatus.IN_REALIZATION
            "3" -> OfferStatus.DONE
            else -> OfferStatus.NEW
        }
        val statusName = OfferStatusDescription.get(status).getText(service)
        val body = service.resources.getString(R.string.notification__status_change_body, statusName)
        showNotification(offerId, title, body, offerId)
    }

    private fun handleReadMessage(notificationData: Map<String, String>) {
        val offerId = notificationData["offerId"] ?: return
        cancelNotification(offerId)
    }

    protected fun showNotification(id: String, title: String, body: String, offerId: String? = null) {
        if(isAppInForeground() && !isNotificationVisible(id)) {
            return
        }

        val intId = id.hashCode()

        val largeIconDrawable = BitmapFactory.decodeResource(service.applicationContext.resources,
            largeIcon)

        val builder = NotificationCompat.Builder(service, channelId)
            .setPriority(NOTIFICATION_PRIORITY)
            .setSmallIcon(smallIcon)
            .setLargeIcon(largeIconDrawable)
            .setContentTitle(title)
            .setContentText(body)

        if(offerId != null) {
            val intent = getSplashIntent()
            val bundle = bundleOf(
                "offerId" to offerId,
            )
            intent.putExtras(bundle)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            val pendingIntent = PendingIntent.getActivity(service.applicationContext, intId, intent, 0)
            builder.setContentIntent(pendingIntent)
            builder.setAutoCancel(true)
        }

        NotificationManagerCompat.from(service).notify(intId, builder.build())
    }

    abstract fun getSplashIntent(): Intent

    private fun cancelNotification(id: String) {
        val intId = id.hashCode()
        NotificationManagerCompat.from(service).cancel(intId)
    }

    private fun isAppInForeground(): Boolean {
        val appProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(appProcessInfo)
        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE)
    }

    private fun isNotificationVisible(id: String): Boolean {
        val mNotificationManager = getSystemService(service, NotificationManager::class.java)
        val notifications = mNotificationManager?.activeNotifications ?: return false
        for (notification in notifications) {
            if(notification.id == id.hashCode()) {
                return true
            }
        }
        return false
    }

}
