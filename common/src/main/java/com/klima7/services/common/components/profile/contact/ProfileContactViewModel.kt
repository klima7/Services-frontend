package com.klima7.services.common.components.profile.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.ExpertInfo
import com.klima7.services.common.platform.BaseViewModel

class ProfileContactViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class Call(val phoneNumber: String): Event()
        data class SendEmail(val emailAddress: String): Event()
        data class OpenWebsite(val url: String): Event()
    }

    val info = MutableLiveData<ExpertInfo>()

    val phoneVisible = info.map { it.phone != null }
    val emailVisible = info.map { it.email != null }
    val websiteVisible = info.map { it.website != null }
    val noContactVisible = info.map { it.phone == null && it.email == null && it.website == null }

    val phoneText = info.map { formatPhone(it.phone) }
    val emailText = info.map { it.email }
    val websiteText = info.map { it.website }

    fun setInfo(info: ExpertInfo) {
        this.info.value = info
    }

    fun contactPhoneClicked() {
        val phone = info.value?.phone
        if(phone != null) {
            sendEvent(Event.Call(phone))
        }
    }

    fun contactEmailClicked() {
        val email = info.value?.email
        if(email != null) {
            sendEvent(Event.SendEmail(email))
        }
    }

    fun contactWebsiteClicked() {
        val website = info.value?.website
        if(website != null) {
            sendEvent(Event.OpenWebsite(website))
        }
    }

    private fun formatPhone(phone: String?): String? {
        if (phone == null || phone.length != 9)
            return phone
        return "${phone.substring(0, 3)} ${phone.substring(3, 6)} ${phone.substring(6, 9)}"
    }

}