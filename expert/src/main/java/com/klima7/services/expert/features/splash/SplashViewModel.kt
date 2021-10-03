package com.klima7.services.expert.features.splash

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.util.getOrElse
import com.klima7.services.expert.ExpertNavigator
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository,
    private val navigator: ExpertNavigator
): BaseViewModel() {

    fun goToNextScreen(activity: Activity) {
        viewModelScope.launch {

            val authorized = authRepository.getUid().getOrElse(null)

            navigator.showHomeScreen(activity)
//            val expert = expertsRepository.getExpert("expert1")
//            expert.onSuccess { Log.i("Helloo", ""+expert) }
        }
    }

}