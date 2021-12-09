package com.klima7.services.common.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.klima7.services.common.R

abstract class BaseNotificationManager(
    private val service: Service
) {

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
        Log.i("Hello", "Message received: ${notificationData["type"]}]")
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
            else -> false
        }
    }

    private fun handleTextMessage(notificationData: Map<String, String>) {
        val senderName = notificationData["sender"] ?: return
        val message = notificationData["message"] ?: return
        val offerId = notificationData["offerId"] ?: return
        val title = service.resources.getString(R.string.text_message_from, senderName)
        showNotification(offerId, title, message)
    }

    private fun handleImageMessage(notificationData: Map<String, String>) {
        val senderName = notificationData["sender"] ?: return
        val offerId = notificationData["offerId"] ?: return
        val title = service.resources.getString(R.string.text_message_from, senderName)
        val body = service.resources.getString(R.string.image_message_sent)
        showNotification(offerId, title, body)
    }

    private fun handleReadMessage(notificationData: Map<String, String>) {
        val offerId = notificationData["offerId"] ?: return
        cancelNotification(offerId)
    }

    private fun showNotification(id: String, title: String, body: String, intent: PendingIntent? = null) {
        val largeIconDrawable = BitmapFactory.decodeResource(service.applicationContext.resources,
            largeIcon)

        val builder = NotificationCompat.Builder(service, channelId)
            .setPriority(NOTIFICATION_PRIORITY)
            .setSmallIcon(smallIcon)
            .setLargeIcon(largeIconDrawable)
            .setContentTitle(title)
            .setContentText(body)

        if(intent != null) {
            builder.setContentIntent(intent)
        }

        val intId = id.hashCode()
        NotificationManagerCompat.from(service).notify(intId, builder.build())
    }

    private fun cancelNotification(id: String) {
        val intId = id.hashCode()
        NotificationManagerCompat.from(service).cancel(intId)
    }

}
