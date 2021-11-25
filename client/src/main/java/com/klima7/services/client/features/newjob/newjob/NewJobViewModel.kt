package com.klima7.services.client.features.newjob.newjob

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.Service
import com.klima7.services.common.models.SimpleLocation
import com.klima7.services.common.models.SimpleService
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewJobViewModel: BaseViewModel() {

    enum class Screen {
        SERVICE, LOCATION, DETAILS
    }

    enum class Direction {
        BACK, FORTH
    }

    companion object {
        val screensOrder = listOf(Screen.SERVICE, Screen.LOCATION, Screen.DETAILS)
    }

    sealed class Event: BaseEvent() {
        data class Navigate(val screen: Screen, val direction: Direction): Event()
    }

    val subtitle = MutableLiveData<String>()
    val screen = MutableLiveData<Screen>()
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
        this.screen.value = Screen.DETAILS
        sendEvent(Event.Navigate(Screen.DETAILS, Direction.FORTH))
    }

    private fun getProgressPosition(screen: Screen): Int {
        return when(screen) {
            Screen.SERVICE -> 1
            Screen.LOCATION -> 2
            Screen.DETAILS -> 3
        }
    }

}