package com.klima7.services.client.features.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel

class CategoryViewModel(
    private val getAllCategoriesUC: GetAllCategoriesUC,
): BaseViewModel() {

    val categories = MutableLiveData<List<Category>>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    init {
        loadCategories()
    }

    fun refresh() {
        loadCategories()
    }

    private fun loadCategories() {
        loadState.value = LoadAreaView.State.LOAD
        getAllCategoriesUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { categories ->
                loadState.value = LoadAreaView.State.MAIN
                this.categories.value = categories
            }
        )
    }

}