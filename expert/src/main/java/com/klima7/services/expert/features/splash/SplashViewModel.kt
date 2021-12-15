package com.klima7.services.expert.features.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.splash.BaseSplashViewModel
import com.klima7.services.common.core.None
import com.klima7.services.expert.messaging.UpdateTokenUC

class SplashViewModel(
    getCurrentExpertStateUC: GetCurrentExpertStateUC
): BaseSplashViewModel(getCurrentExpertStateUC) {

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
