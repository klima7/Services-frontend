package com.klima7.services.common.components.settings

import com.klima7.services.common.platform.BaseViewModel

data class SettingsOption(
    val iconRes: Int,
    val textRes: Int,
    val event: BaseViewModel.BaseEvent
)