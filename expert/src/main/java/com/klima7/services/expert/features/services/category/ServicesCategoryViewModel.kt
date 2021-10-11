package com.klima7.services.expert.features.services.category

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.features.services.CategorizedSelectableServices
import com.klima7.services.expert.features.services.SelectableService

class ServicesCategoryViewModel: BaseViewModel() {

    val name = MutableLiveData("Unnamed")
    val services = MutableLiveData<List<SelectableService>>()
    val expanded = MutableLiveData(false)

    fun setServices(cSelectableServices: CategorizedSelectableServices) {
        services.value = cSelectableServices.services
        name.value = cSelectableServices.category.name
    }

    fun getSelectedServices(): List<Service> {
        return services.value?.filter { it.selected }?.map { it.service } ?: listOf()
    }

}