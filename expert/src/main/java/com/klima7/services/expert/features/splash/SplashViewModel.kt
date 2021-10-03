package com.klima7.services.expert.features.splash

import android.app.Activity
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.data.repo.ExpertsRepository
import com.klima7.services.common.domain.util.onSuccess
import com.klima7.services.expert.ExpertNavigator
import kotlinx.coroutines.launch

class SplashViewModel(
    private val expertsRepository: ExpertsRepository,
    private val navigator: ExpertNavigator
): BaseViewModel() {

    fun goToNextScreen(activity: Activity) {
        viewModelScope.launch {
            navigator.showHomeScreen(activity)
//            val expert = expertsRepository.getExpert("expert1")
//            expert.onSuccess { Log.i("Helloo", ""+expert) }
        }
    }

}