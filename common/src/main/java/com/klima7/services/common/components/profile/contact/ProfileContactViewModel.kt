package com.klima7.services.common.components.profile.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.ExpertInfo
import com.klima7.services.common.platform.BaseViewModel

class ProfileContactViewModel: BaseViewModel() {

    val info = MutableLiveData<ExpertInfo>()

    val phoneVisible = info.map { it.phone != null }
    val emailVisible = info.map { it.email != null }
    val websiteVisible = info.map { it.website != null }
    val noContactVisible = info.map { it.phone == null && it.email == null && it.website == null }

    val phoneText = info.map { it.phone }
    val emailText = info.map { it.email }
    val websiteText = info.map { it.website }

    fun setInfo(info: ExpertInfo) {
        this.info.value = info
    }

}