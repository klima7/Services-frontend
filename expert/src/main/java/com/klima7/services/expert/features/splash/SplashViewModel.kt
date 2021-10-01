package com.klima7.services.expert.features.splash

import androidx.lifecycle.viewModelScope
import com.klima7.services.common.base.BaseViewModel
import com.klima7.services.common.repo.ExpertsRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val expertsRepository: ExpertsRepository
): BaseViewModel() {

    fun test() {
        viewModelScope.launch {
            expertsRepository.getExpert("expert1")
        }
    }

}