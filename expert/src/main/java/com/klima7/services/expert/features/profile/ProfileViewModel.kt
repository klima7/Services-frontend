package com.klima7.services.expert.features.profile

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.profile.BaseProfileViewModel
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.expert.usecases.GetCurrentExpertUC

class ProfileViewModel(
    private val getCurrentExpertUC: GetCurrentExpertUC
): BaseProfileViewModel() {

    fun start() {
        loadContent()
    }

    override fun loadContent() {
        loadState.value = LoadAreaView.State.PENDING
        getCurrentExpertUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { expert ->
                this.expert.value = expert
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }
}