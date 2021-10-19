package com.klima7.services.expert.features.services

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.ui.base.BaseLoadViewModel

class ServicesViewModel(
    private val getCategorisedAndMarkedServices: GetCategorisedAndMarkedServices,
    private val setCurrentExpertServices: SetCurrentExpertServices
): BaseLoadViewModel() {

    sealed class Event: BaseEvent() {
        data class SetServices(val services: List<CategorizedSelectableServices>): Event()
        data class ShowSaveFailure(val failure: Failure): Event()
        object Finish: Event()
    }

    fun started() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    fun saveClicked(services: List<Service>) {
        save(services)
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
        setCurrentExpertServices.start(
            viewModelScope,
            SetCurrentExpertServices.Params(services),
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