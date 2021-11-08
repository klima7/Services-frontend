package com.klima7.services.common.components.profile.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel

class ProfileServicesViewModel(
    private val getServicesFromIds: GetServicesFromIds
): BaseViewModel() {

    private var servicesIds = listOf<String>()
    val services = MutableLiveData<List<Service>>()
    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun setServicesIds(servicesIds: List<String>) {
        if(servicesIds != this.servicesIds) {
            this.servicesIds = servicesIds
            loadContent(servicesIds)
        }
    }

    fun refresh() {
        val cServicesIds = servicesIds
        loadContent(cServicesIds)
    }

    private fun loadContent(servicesIds: List<String>) {
        loadState.value = LoadAreaView.State.PENDING
        getServicesFromIds.start(
            viewModelScope,
            GetServicesFromIds.Params(servicesIds),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { services ->
                this.services.value = services
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}