package com.klima7.services.expert.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.ui.loadable.LoadableViewModel
import kotlinx.coroutines.launch

class SetupContentViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): LoadableViewModel() {

    val profileConfigured = MutableLiveData(false)
    val servicesConfigured = MutableLiveData(false)
    val locationConfigured = MutableLiveData(false)

    sealed class Event: BaseEvent() {
        object ShowHomeScreen: Event()
        object ShowInfoScreen: Event()
        object ShowServicesScreen: Event()
        object ShowLocationScreen: Event()
    }

    fun started() {
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    fun continueClicked() {
        sendEvent(Event.ShowHomeScreen)
    }

    fun infoClicked() {
        sendEvent(Event.ShowInfoScreen)
    }

    fun servicesClicked() {
        sendEvent(Event.ShowServicesScreen)
    }

    fun locationClicked() {
        sendEvent(Event.ShowLocationScreen)
    }

    fun configDone() {
        loadContent()
    }

    private fun loadContent() {
        showLoading()
        viewModelScope.launch {
            getUidPart()
        }
    }

    private suspend fun getUidPart() {
        authRepository.getUid().foldS({ failure ->
            showFailure(failure)
        }, { uid ->
            if(uid == null) {
                showFailure(Failure.UnknownFailure)
            }
            else {
                getExpertPart(uid)
            }
        })
    }

    private suspend fun getExpertPart(uid: String) {
        expertsRepository.getExpert(uid).foldS({ failure ->
            showFailure(failure)
        }, { expert ->
            if(expert.fromCache) {
                showFailure(Failure.InternetFailure)
            }
            else {
                verifyExpertPart(expert)
                showMain()
            }
        })
    }

    private fun verifyExpertPart(expert: Expert) {
        profileConfigured.value = expert.info.name != null
        servicesConfigured.value = expert.servicesIds.isNotEmpty()
        locationConfigured.value = expert.area != null
    }

}