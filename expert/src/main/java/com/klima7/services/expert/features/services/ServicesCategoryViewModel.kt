package com.klima7.services.expert.features.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ServicesCategoryViewModel: BaseViewModel() {

    val name = MutableLiveData("Unnamed")
    val services = MutableLiveData<List<SelectableService>>()
    val expanded = MutableLiveData(false)

    fun setServices(services: List<Service>) {
        this.services.value = services.map { SelectableService(it) }
    }

    fun getSelectedServices(): List<Service> {
        return services.value?.filter { it.selected }?.map { it.service } ?: listOf()
    }

    fun setName(name: String) {
        this.name.value = name
    }

}