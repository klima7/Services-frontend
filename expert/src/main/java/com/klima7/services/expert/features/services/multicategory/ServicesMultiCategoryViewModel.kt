package com.klima7.services.expert.features.services.multicategory

import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.features.services.CategorizedSelectableServices

class ServicesMultiCategoryViewModel: BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class SetServices(val services: List<CategorizedSelectableServices>): Event()
    }

    fun setServices(services: List<CategorizedSelectableServices>) {
        sendEvent(Event.SetServices(services))
    }

}