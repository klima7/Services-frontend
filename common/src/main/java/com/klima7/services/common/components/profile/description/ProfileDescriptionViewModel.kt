package com.klima7.services.common.components.profile.description

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseViewModel

class ProfileDescriptionViewModel: BaseViewModel() {

    private val expert = MutableLiveData<Expert>()
    val description = expert.map { expert -> expert.info.description }
    val noDescriptionVisible = description.map { it == null }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

}