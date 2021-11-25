package com.klima7.services.client.features.jobsetup.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel

class ServiceViewModel(
    private val getServicesFromCategoryUC: GetServicesFromCategoryUC,
): BaseViewModel() {

    val category = MutableLiveData<Category>()
    val categoryName = category.map { it.name }
    val services = MutableLiveData<List<Service>>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun setCategory(category: Category) {
        this.category.value = category
        loadServices(category.id)
    }

    fun refresh() {
        val cCategory = category.value;
        if(cCategory != null) {
            loadServices(cCategory.id)
        }
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