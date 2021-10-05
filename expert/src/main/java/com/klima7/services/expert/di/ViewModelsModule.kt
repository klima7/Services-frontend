package com.klima7.services.expert.di

import com.klima7.services.expert.features.home.HomeViewModel
import com.klima7.services.expert.features.info.InfoViewModel
import com.klima7.services.expert.features.login.LoginViewModel
import com.klima7.services.expert.features.setup.SetupContentViewModel
import com.klima7.services.expert.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { SplashViewModel(get(), get()) }
    viewModel { HomeViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SetupContentViewModel(get(), get()) }
    viewModel { InfoViewModel() }

}
