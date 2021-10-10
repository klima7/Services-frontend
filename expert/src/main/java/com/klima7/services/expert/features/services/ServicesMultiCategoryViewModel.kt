package com.klima7.services.expert.features.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.base.BaseViewModel

class ServicesMultiCategoryViewModel: BaseViewModel() {

    val categorizedServices = MutableLiveData<List<CategorizedServices>>()

    sealed class Event: BaseEvent() {
        data class SetServices(val services: List<CategorizedServices>): Event()
    }

    fun setServices(services: List<CategorizedServices>) {
        Log.i("Hello", "Setting services in viewModel: $services")
        this.categorizedServices.value = services
        sendEvent(Event.SetServices(services))
    }

    fun getSelectedServices(): List<Service> {
        return listOf()
    }

}