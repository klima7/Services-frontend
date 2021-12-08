package com.klima7.services.expert.features.settings

import com.klima7.services.common.components.settings.BaseSettingsViewModel
import com.klima7.services.expert.usecases.SignOutExpertUC

class SettingsViewModel(
    signOutExpertUC: SignOutExpertUC
): BaseSettingsViewModel(signOutExpertUC) {

    sealed class Event: BaseEvent() {
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowWorkingAreaScreen: Event()
        object ShowDeleteScreen: Event()
    }

}