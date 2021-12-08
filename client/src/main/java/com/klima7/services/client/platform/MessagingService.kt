package com.klima7.services.client.platform

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import com.klima7.services.client.features.splash.SplashActivity
import com.klima7.services.common.R
import java.lang.Exception

class MessagingService: FirebaseMessagingService() {

    private val messaging = Firebase.messaging
    private val TAG = "MyFirebaseMsgService"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun getStartCommandIntent(p0: Intent): Intent {
        Log.i("Hello", "getStartCommandIntent")
        return super.getStartCommandIntent(p0)
    }

    override fun handleIntent(intent: Intent) {
        Log.i("Hello", "handleIntent")
        super.handleIntent(intent)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i("Hello", "Message read")

        val screen = remoteMessage.data["screen"]
        val argument = remoteMessage.data["argument"] ?: ""
        Log.i("Hello", "$screen $argument")

        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("screen", "screen")
        intent.putExtra("argument", argument)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, "792922250")
            .setSmallIcon(R.drawable.avatar_default)
            .setContentTitle(remoteMessage.data["type"] ?: "no-type")
            .setContentText("Client")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    override fun onDeletedMessages() {
        Log.i("Hello", "onDeletedMessages")
        super.onDeletedMessages()
    }

    override fun onMessageSent(p0: String) {
        Log.i("Hello", "onMessageSent")
        super.onMessageSent(p0)
    }

    override fun onSendError(p0: String, p1: Exception) {
        Log.i("Hello", "onSendError")
        super.onSendError(p0, p1)
    }

    override fun onNewToken(p0: String) {
        Log.i("Hello", "onNewToken")
        super.onNewToken(p0)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "abc"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("792922250", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}