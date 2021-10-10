package com.klima7.services.expert.features.services

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.base.BaseViewModel

class ServicesCategoryViewModel: BaseViewModel() {

    val name = MutableLiveData("Unnamed")
    val services = MutableLiveData<List<SelectableService>>()
    val expanded = MutableLiveData(false)

    fun setServices(cServices: CategorizedServices) {
        services.value = cServices.services.map { SelectableService(it) }
        name.value = cServices.category.name
    }

    fun getSelectedServices(): List<Service> {
        return services.value?.filter { it.selected }?.map { it.service } ?: listOf()
    }

}