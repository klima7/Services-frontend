package com.klima7.services.expert.features.info

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.fragments.FailurableViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoViewModel: FailurableViewModel() {

    fun doSomething() {
        viewModelScope.launch {
            delay(1000)
            showFailure(Failure.InternetFailure)
        }
    }

    override fun refresh() {
        Log.i("Hello", "Refreshing fragment")
        doSomething()
    }

}