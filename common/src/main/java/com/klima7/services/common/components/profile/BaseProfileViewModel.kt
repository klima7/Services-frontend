package com.klima7.services.common.components.profile

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel

abstract class BaseProfileViewModel: BaseViewModel() {

    val expert = MutableLiveData<Expert>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun refresh() {
        loadContent()
    }

    protected abstract fun loadContent()

}