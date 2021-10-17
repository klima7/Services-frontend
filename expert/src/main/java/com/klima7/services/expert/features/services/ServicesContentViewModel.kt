package com.klima7.services.expert.features.services

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.ui.loadable.LoadableViewModel

class ServicesContentViewModel(
    private val getCategorisedAndMarkedServices: GetCategorisedAndMarkedServices,
    private val setCurrentExpertServices: SetCurrentExpertServices
): LoadableViewModel() {

    sealed class Event: BaseEvent() {
        data class SetServices(val services: List<CategorizedSelectableServices>): Event()
    }

    fun started() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    fun servicesSelected(services: List<Service>) {
        setServices(services)
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

    private fun setServices(services: List<Service>) {
        showPending()
        setCurrentExpertServices.start(
            viewModelScope,
            SetCurrentExpertServices.Params(services),
            {
                // TODO: failure action
                showMain()
            }, {
                // TODO: success action
                showMain()
            }
        )
    }
}