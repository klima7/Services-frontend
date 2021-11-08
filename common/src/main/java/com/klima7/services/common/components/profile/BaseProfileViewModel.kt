package com.klima7.services.common.components.profile

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseLoadViewModel

abstract class BaseProfileViewModel: BaseLoadViewModel() {

    val expert = MutableLiveData<Expert>()

    override fun refresh() {
        loadContent()
    }

    protected abstract fun loadContent()

}