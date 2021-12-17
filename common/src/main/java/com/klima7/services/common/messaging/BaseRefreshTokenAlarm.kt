package com.klima7.services.common.messaging

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager

import android.app.PendingIntent

abstract class BaseRefreshTokenAlarm: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(context == null) {
            return
        }
        performAction(context)
    }

    abstract fun performAction(context: Context)

    fun setAlarm(context: Context) {
        val alarmService = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, this::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmService.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            (1000 * 60 * 60 * 24 * 7).toLong(),
            pendingIntent
        )
    }
}