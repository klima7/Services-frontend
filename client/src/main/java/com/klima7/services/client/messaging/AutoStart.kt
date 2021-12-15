package com.klima7.services.client.messaging

import com.klima7.services.common.messaging.BaseAutoStart

class AutoStart: BaseAutoStart() {

    override val refreshTokenAlarm = RefreshTokenAlarm()

}