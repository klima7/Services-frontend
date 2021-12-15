package com.klima7.services.expert.messaging

import com.klima7.services.common.messaging.BaseAutoStart

class AutoStart: BaseAutoStart() {

    override val refreshTokenAlarm = RefreshTokenAlarm()

}