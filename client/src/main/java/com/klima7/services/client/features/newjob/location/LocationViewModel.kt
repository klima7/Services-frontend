package com.klima7.services.client.features.newjob.location

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel

class LocationViewModel: BaseViewModel() {

    private lateinit var serviceId: String
    val serviceName = MutableLiveData<String>()

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

    fun start(categoryId: String, categoryName: String) {
        this.serviceId = categoryId
        this.serviceName.value = categoryName
    }

    fun refresh() {
    }

}