package com.klima7.services.common.lib.avatar

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.lib.base.BaseViewModel

class AvatarViewModel: BaseViewModel() {

    data class ImageData(val uri: String, val signature: Long)

    val imageData = MutableLiveData<ImageData>(null)

    fun setUri(uri: String) {
        val signature = System.currentTimeMillis()
        this.imageData.value = ImageData(uri, signature)
    }

}