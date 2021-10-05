package com.klima7.services.expert.features.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.lib.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SetupViewModel: BaseViewModel() {

    val noInternetVisible = MutableLiveData(false)
    val profileTileVisible = MutableLiveData(true)
    val serviceTileVisible = MutableLiveData(false)
    val locationTileVisible = MutableLiveData(true)

    init {
        viewModelScope.launch {
            delay(2000)
            serviceTileVisible.value = true
            delay(2000)
            noInternetVisible.value = true
        }
    }
}