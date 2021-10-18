package com.klima7.services.common.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.ui.loadable.LoadableViewModel

class ProfileContentViewModel(
    private val getExpertUC: GetExpertUC
): LoadableViewModel() {

    val expert = MutableLiveData<Expert>()

    fun start() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    private fun loadContent() {
        showLoading()
        getExpertUC.start(
            viewModelScope,
            GetExpertUC.Params("GVUPHpMgt36NtVg9vsClFSaaOQQ7"),
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