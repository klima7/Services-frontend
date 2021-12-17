package com.klima7.services.common.messaging

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

abstract class BaseAutoStart: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(context == null) {
            return
        }

        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED))
        {
            schedule(context)
        }
    }

    abstract fun schedule(context: Context)
}