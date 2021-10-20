package com.klima7.services.common.ui.profile.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.ExpertInfo
import com.klima7.services.common.ui.base.BaseViewModel

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