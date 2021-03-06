package com.klima7.services.client.features.jobsetup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.SimpleLocation
import com.klima7.services.common.models.SimpleService
import com.klima7.services.common.platform.BaseViewModel

class JobSetupViewModel: BaseViewModel() {

    enum class Screen {
        SERVICE, LOCATION, JOB_DETAILS, JOB_CREATED
    }

    enum class Direction {
        BACK, FORTH
    }

    companion object {
        val screensOrder = listOf(
            Screen.SERVICE,
            Screen.LOCATION,
            Screen.JOB_DETAILS,
            Screen.JOB_CREATED
        )
    }

    sealed class Event: BaseEvent() {
        data class Navigate(val screen: Screen, val direction: Direction): Event()
        object ShowJobsScreen: Event()
    }

    private val screen = MutableLiveData<Screen>()
    val subtitle = screen.map(this::createSubtitle)
    val progressPosition = screen.map(this::getProgressPosition)

    val category = MutableLiveData<Category>()
    val service = MutableLiveData<SimpleService>()
    val location = MutableLiveData<SimpleLocation>()

    fun startService(category: Category) {
        this.category.value = category
        this.screen.value = Screen.SERVICE
    }

    fun startLocation(service: SimpleService) {
        this.service.value = service
        this.screen.value = Screen.LOCATION
    }

    fun backClicked() {
        val cScreen = screen.value ?: return

        if(cScreen == Screen.JOB_CREATED) {
            showJobsScreen()
        }

        val pos = screensOrder.indexOf(cScreen)
        if(pos == 0) {
            sendEvent(BaseEvent.FinishActivity)
        }
        else {
            val prevScreen = screensOrder[pos-1]
            screen.value = prevScreen
            sendEvent(Event.Navigate(prevScreen, Direction.BACK))
        }
    }

    fun setService(service: SimpleService) {
        this.service.value = service
    }

    fun setLocation(location: SimpleLocation) {
        this.location.value = location
    }

    fun showLocationScreen() {
        this.screen.value = Screen.LOCATION
        sendEvent(Event.Navigate(Screen.LOCATION, Direction.FORTH))
    }

    fun showDetailsScreen() {
        this.screen.value = Screen.JOB_DETAILS
        sendEvent(Event.Navigate(Screen.JOB_DETAILS, Direction.FORTH))
    }

    fun showCreatedScreen() {
        this.screen.value = Screen.JOB_CREATED
        sendEvent(Event.Navigate(Screen.JOB_CREATED, Direction.FORTH))
    }

    fun showJobsScreen() {
        sendEvent(Event.ShowJobsScreen)
    }

    private fun getProgressPosition(screen: Screen): Int {
        return when(screen) {
            Screen.SERVICE -> 1
            Screen.LOCATION -> 2
            Screen.JOB_DETAILS -> 3
            Screen.JOB_CREATED -> 3
        }
    }

    private fun createSubtitle(screen: Screen): String {
        return when(screen) {
            Screen.SERVICE -> this.category.value?.name ?: ""
            Screen.LOCATION -> this.service.value?.name ?: ""
            Screen.JOB_DETAILS -> this.service.value?.name ?: ""
            Screen.JOB_CREATED -> "Stworzono!"
        }
    }

}