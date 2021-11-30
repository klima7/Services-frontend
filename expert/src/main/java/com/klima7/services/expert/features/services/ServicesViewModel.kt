package com.klima7.services.expert.features.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.models.CategoryWithServices
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel

class ServicesViewModel(
    private val getAllAndSelectedServicesUC: GetAllAndSelectedServicesUC,
    private val setCurrentExpertServicesUC: SetCurrentExpertServicesUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowSaveFailure(val failure: Failure): Event()
        object Finish: Event()
    }

    val allServices = MutableLiveData<Set<CategoryWithServices>?>()
    val selectedServices = MutableLiveData<Set<String>?>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    fun started() {
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun saveClicked(services: List<Service>) {
//        lastServices = services
//        save(services)
    }

    fun retrySave() {
//        lastServices?.let { services ->
//            save(services)
//        }
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getAllAndSelectedServicesUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            }, { allAndSelectedServices ->
                if(allAndSelectedServices.selectedIds.isEmpty()) {
                    loadFailure.value = Failure.InternetFailure
                    loadState.value = LoadAreaView.State.FAILURE
                }
                else {
                    loadState.value = LoadAreaView.State.MAIN
                }
            }
        )
    }

    private fun save(services: List<Service>) {
        loadState.value = LoadAreaView.State.PENDING
        setCurrentExpertServicesUC.start(
            viewModelScope,
            SetCurrentExpertServicesUC.Params(services),
            { failure ->
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowSaveFailure(failure))
            }, {
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.Finish)
            }
        )
    }
}
