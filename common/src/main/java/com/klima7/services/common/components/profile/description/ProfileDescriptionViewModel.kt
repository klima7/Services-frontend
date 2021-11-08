package com.klima7.services.common.components.profile.description

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseViewModel

class ProfileDescriptionViewModel: BaseViewModel() {

    val expert = MutableLiveData<Expert>()
    val description = expert.map { expert -> expert.info.description }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

}