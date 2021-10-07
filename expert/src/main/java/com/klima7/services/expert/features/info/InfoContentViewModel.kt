package com.klima7.services.expert.features.info

import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.lib.failurable.FailurableViewModel

class InfoContentViewModel: FailurableViewModel() {

    val name = MutableLiveData<String>()
    val company = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val website = MutableLiveData<String>()

    enum class PhoneError { TooShort }
    enum class EmailError { InvalidFormat }
    enum class WebsiteError { InvalidFormat }

    val phoneError = MutableLiveData<PhoneError?>(null)
    val emailError = MutableLiveData<EmailError?>(null)
    val websiteError = MutableLiveData<WebsiteError?>(null)

    init {
        phone.observeForever { phone ->
            if(phone.isEmpty() || phone.length == 9) {
                phoneError.value = null;
            }
            else {
                phoneError.value = PhoneError.TooShort
            }
        }

        email.observeForever { email ->
            if(email.isEmpty() || isEmailValid(email)) {
                emailError.value = null;
            }
            else {
                emailError.value = EmailError.InvalidFormat
            }
        }

        website.observeForever { website ->
            if(website.isEmpty() || isWebsiteAddressValid(website)) {
                websiteError.value = null;
            }
            else {
                websiteError.value = WebsiteError.InvalidFormat
            }
        }
    }

    fun doSomething() {
    }

    fun saveClicked() {

    }

    override fun refresh() {
        doSomething()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun isWebsiteAddressValid(websiteAddress: String): Boolean {
        return URLUtil.isValidUrl(websiteAddress)
    }

}