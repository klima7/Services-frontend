package com.klima7.services.client.messaging

import android.app.Service
import android.content.Intent
import com.klima7.services.client.R
import com.klima7.services.client.features.splash.SplashActivity
import com.klima7.services.common.messaging.BaseNotificationManager

class NotificationManager(
    private val service: Service,
): BaseNotificationManager(service) {

    override val channelId = "services clients";
    override val largeIcon = R.drawable.icon_notification_large
    override val smallIcon = R.drawable.icon_notification_small

    override fun getSplashIntent(): Intent {
        return Intent(service.applicationContext, SplashActivity::class.java)
    }
}