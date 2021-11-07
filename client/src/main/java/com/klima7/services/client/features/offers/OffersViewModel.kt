package com.klima7.services.client.features.offers

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.platform.BaseViewModel

class OffersViewModel: BaseViewModel() {

    val subtitle = MutableLiveData("Składanie mebli")
    val loadState = MutableLiveData(LoadAreaView.State.MAIN)

    fun refresh() {

    }

}