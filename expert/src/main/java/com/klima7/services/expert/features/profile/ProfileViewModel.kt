package com.klima7.services.expert.features.profile

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.profile.BaseProfileViewModel
import com.klima7.services.common.core.None
import com.klima7.services.expert.usecases.GetCurrentExpertUC

class ProfileViewModel(
    private val getCurrentExpertUC: GetCurrentExpertUC
): BaseProfileViewModel() {

    fun start() {
        loadContent()
    }

    override fun loadContent() {
        showPending()
        getCurrentExpertUC.start(
            viewModelScope,
            None(),
            { failure ->
                showFailure(failure)
            },
            { expert ->
                this.expert.value = expert
                showMain()
            }
        )
    }
}