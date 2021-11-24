package com.klima7.services.client.features.newjob.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel

class ServiceViewModel(
    private val getServicesFromCategoryUC: GetServicesFromCategoryUC,
): BaseViewModel() {

    private lateinit var categoryId: String
    val services = MutableLiveData<List<Service>>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun start(categoryId: String) {
        this.categoryId = categoryId
        loadServices(categoryId)
    }

    fun refresh() {
        loadServices(categoryId)
    }

    private fun loadServices(categoryId: String) {
        loadState.value = LoadAreaView.State.LOAD
        getServicesFromCategoryUC.start(
            viewModelScope,
            GetServicesFromCategoryUC.Params(categoryId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { services ->
                loadState.value = LoadAreaView.State.MAIN
                this.services.value = services
            }
        )
    }

}