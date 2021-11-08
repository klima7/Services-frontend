package com.klima7.services.client.features.profile

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.profile.BaseProfileViewModel

class ProfileContentViewModel(
    private val getExpertByUidUC: GetExpertByUidUC
): BaseProfileViewModel() {

    private lateinit var expertUid: String

    fun start(expertUid: String) {
        this.expertUid = expertUid
        loadContent()
    }

    override fun loadContent() {
        showLoading()
        getExpertByUidUC.start(
            viewModelScope,
            GetExpertByUidUC.Params(expertUid),
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