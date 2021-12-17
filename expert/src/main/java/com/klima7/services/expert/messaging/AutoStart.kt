package com.klima7.services.expert.messaging

import android.content.Context
import com.klima7.services.common.messaging.BaseAutoStart

class AutoStart: BaseAutoStart() {

    override fun schedule(context: Context) {
        RefreshTokenWorker.schedule(context)
    }

}