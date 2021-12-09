package com.klima7.services.common.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

abstract class BaseNotificationsHandler(
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
        return false
    }

    fun showNotification(id: Int, title: String, body: String, intent: PendingIntent? = null) {
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

        NotificationManagerCompat.from(service).notify(id, builder.build())
    }

}