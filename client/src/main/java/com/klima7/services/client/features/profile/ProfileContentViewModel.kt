package com.klima7.services.client.features.profile

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.profile.BaseProfileViewModel
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.usecases.GetExpertUC

class ProfileContentViewModel(
    private val getExpertUC: GetExpertUC
): BaseProfileViewModel() {

    private lateinit var expertUid: String

    fun start(expertUid: String) {
        this.expertUid = expertUid
        loadContent()
    }

    override fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getExpertUC.start(
            viewModelScope,
            GetExpertUC.Params(expertUid),
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