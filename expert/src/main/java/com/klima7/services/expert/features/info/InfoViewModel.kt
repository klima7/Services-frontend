package com.klima7.services.expert.features.info

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.failurable.FailurableViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoViewModel: FailurableViewModel() {

    fun doSomething() {
        viewModelScope.launch {
            delay(1000)
            showFailure(Failure.InternetFailure)
            delay(1000)
            showFailure(Failure.NotFoundFailure)
            delay(1000)
            showFailure(Failure.PermissionFailure)
            delay(1000)
            showFailure(Failure.UnknownFailure)
            delay(1000)
            showFailure(Failure.ServerFailure)
            delay(1000)
        }
    }

    override fun refresh() {
        Log.i("Hello", "Refreshing fragment")
        doSomething()
    }

}