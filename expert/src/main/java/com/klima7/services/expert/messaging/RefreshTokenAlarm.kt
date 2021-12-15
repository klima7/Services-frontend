package com.klima7.services.expert.messaging

import android.content.Context
import com.klima7.services.common.core.None
import com.klima7.services.common.messaging.BaseRefreshTokenAlarm
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RefreshTokenAlarm: BaseRefreshTokenAlarm() {

    private val updateTokenUC = UpdateTokenUC()

    @DelicateCoroutinesApi
    override fun performAction(context: Context) {
        GlobalScope.launch {
            updateTokenUC.run(None())
        }
    }

}