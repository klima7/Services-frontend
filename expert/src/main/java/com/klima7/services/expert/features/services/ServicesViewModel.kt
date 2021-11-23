package com.klima7.services.expert.features.services

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.core.None
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseLoadViewModel

class ServicesViewModel(
    private val getCategorisedAndMarkedServices: GetCategorisedAndMarkedServicesUC,
    private val setCurrentExpertServicesUC: SetCurrentExpertServicesUC
): BaseLoadViewModel() {

    sealed class Event: BaseEvent() {
        data class SetServices(val services: List<CategorizedSelectableServices>): Event()
        data class ShowSaveFailure(val failure: Failure): Event()
        object Finish: Event()
    }

    private var lastServices: List<Service>? = null

    fun started() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    fun saveClicked(services: List<Service>) {
        lastServices = services
        save(services)
    }

    fun retrySave() {
        lastServices?.let { services ->
            save(services)
        }
    }

    private fun loadContent() {
        showLoading()
        getCategorisedAndMarkedServices.start(
            viewModelScope,
            None(),
            { failure ->
                showFailure(failure)
            }, { services ->
                if(services.isEmpty()) {
                    showFailure(Failure.InternetFailure)
                }
                else {
                    sendEvent(Event.SetServices(services))
                    showMain()
                }
            }
        )
    }

    private fun save(services: List<Service>) {
        showPending()
        setCurrentExpertServicesUC.start(
            viewModelScope,
            SetCurrentExpertServicesUC.Params(services),
            { failure ->
                showMain()
                sendEvent(Event.ShowSaveFailure(failure))
            }, {
                showMain()
                sendEvent(Event.Finish)
            }
        )
    }
}