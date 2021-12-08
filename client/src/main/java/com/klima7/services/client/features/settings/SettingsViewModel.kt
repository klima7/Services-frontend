package com.klima7.services.client.features.settings

import com.klima7.services.client.usecases.SignOutClientUC
import com.klima7.services.common.components.settings.BaseSettingsViewModel

class SettingsViewModel(
    signOutClientUC: SignOutClientUC
): BaseSettingsViewModel(signOutClientUC) {

    sealed class Event: BaseEvent() {
        object ShowInfoScreen: Event()
        object ShowDeleteScreen: Event()
    }

}