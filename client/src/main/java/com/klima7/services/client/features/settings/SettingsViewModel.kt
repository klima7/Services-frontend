package com.klima7.services.client.features.settings

import com.klima7.services.common.components.settings.BaseSettingsViewModel
import com.klima7.services.common.usecases.SignOutUC

class SettingsViewModel(
    signOutUC: SignOutUC
): BaseSettingsViewModel(signOutUC) {

    sealed class Event: BaseEvent() {
        object ShowInfoScreen: Event()
        object ShowDeleteScreen: Event()
    }

}