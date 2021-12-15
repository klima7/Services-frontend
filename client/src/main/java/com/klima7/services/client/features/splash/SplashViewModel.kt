package com.klima7.services.client.features.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.client.messaging.UpdateTokenUC
import com.klima7.services.common.components.splash.BaseSplashViewModel
import com.klima7.services.common.core.None

class SplashViewModel(
    getCurrentClientStateUC: GetCurrentClientStateUC,
): BaseSplashViewModel(getCurrentClientStateUC) {

    override fun started() {
        super.started()
        UpdateTokenUC().start(
            viewModelScope,
            None(),
            {},
            {}
        )
    }

}
