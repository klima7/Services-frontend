package com.klima7.services.client.features.newjob.newjob

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewJobViewModel: BaseViewModel() {

    enum class Screen {
        SERVICE, LOCATION, DETAILS
    }

    companion object {
        val screensOrder = listOf(Screen.SERVICE, Screen.LOCATION, Screen.DETAILS)
    }

    val subtitle = MutableLiveData<String>()
    val screen = MutableLiveData<Screen>()
    val progressPosition = screen.map(this::getProgressPosition)

    val category = MutableLiveData<Category>()
    val service = MutableLiveData<Service>()

    init {
        viewModelScope.launch {
            screen.value = Screen.SERVICE
            delay(2000)
            screen.value = Screen.LOCATION
            delay(2000)
            screen.value = Screen.DETAILS
        }
    }

    fun backClicked() {
        val cScreen = screen.value ?: return
        val pos = screensOrder.indexOf(cScreen)
        if(pos == 0) {
            sendEvent(BaseEvent.FinishActivity)
        }
        else {
            screen.value = screensOrder[pos-1]
        }
    }

    private fun getProgressPosition(screen: Screen): Int {
        return when(screen) {
            Screen.SERVICE -> 1
            Screen.LOCATION -> 2
            Screen.DETAILS -> 3
        }
    }

}