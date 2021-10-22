package com.klima7.services.common.components.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseLoadViewModel

class ProfileViewModel(
    private val getExpertUC: GetExpertUC
): BaseLoadViewModel() {

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
//            GetExpertUC.Params("pnmE94NiTQbRV3tuUWYf0Pj3XHq1"),
            { failure ->
                showFailure(failure)
            },
            { expert ->
                this.expert.value = expert
                showMain()
//                showFailure(Failure.InternetFailure)
            }
        )
    }

}