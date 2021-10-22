package com.klima7.services.common.components.profile.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Service
import com.klima7.services.common.base.BaseLoadViewModel

class ProfileServicesViewModel(
    private val getServicesFromIds: GetServicesFromIds
): BaseLoadViewModel() {

    private var servicesIds = listOf<String>()
    val services = MutableLiveData<List<Service>>()

    fun setServicesIds(servicesIds: List<String>) {
        if(servicesIds != this.servicesIds) {
            this.servicesIds = servicesIds
            loadContent(servicesIds)
        }
    }

    override fun refresh() {
        val cServicesIds = servicesIds
        loadContent(cServicesIds)
    }

    private fun loadContent(servicesIds: List<String>) {
        showPending()
        getServicesFromIds.start(
            viewModelScope,
            GetServicesFromIds.Params(servicesIds),
            { failure ->
                showFailure(failure)
            },
            { services ->
                this.services.value = services
                showMain()
            }
        )
    }

}