package com.klima7.services.expert.features.info

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

    val phoneError = MutableLiveData<PhoneError?>(null)

    init {
        phone.observeForever { phone ->
            if(phone.isEmpty() || phone.length == 9) {
                phoneError.value = null;
            }
            else {
                phoneError.value = PhoneError.TooShort
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

}