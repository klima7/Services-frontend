package com.klima7.services.common.components.profile.header

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseViewModel

class ProfileHeaderViewModel: BaseViewModel() {

    val expert = MutableLiveData<Expert>()
    val name = expert.map { it.info.name }
    val company = expert.map {it.info.company }
    val profileImageUrl = expert.map {it.profileImage }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

}