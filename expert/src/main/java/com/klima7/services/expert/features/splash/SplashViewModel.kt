package com.klima7.services.expert.features.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.splash.BaseSplashViewModel
import com.klima7.services.common.core.None
import com.klima7.services.expert.messaging.UpdateTokenUC

class SplashViewModel(
    getCurrentExpertStateUC: GetCurrentExpertStateUC
): BaseSplashViewModel(getCurrentExpertStateUC) {

    override fun started(offerId: String?) {
        super.started(offerId)
        UpdateTokenUC().start(
            viewModelScope,
            None(),
            {},
            {}
        )
    }

}
