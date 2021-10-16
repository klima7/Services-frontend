package com.klima7.services.common.lib.avatar

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.domain.models.ProfileImage
import com.klima7.services.common.lib.base.BaseViewModel

class AvatarViewModel: BaseViewModel() {

    val profileImage = MutableLiveData<ProfileImage?>(null)

    fun setProfileImage(profileImage: ProfileImage?) {
        this.profileImage.value = profileImage
    }

}