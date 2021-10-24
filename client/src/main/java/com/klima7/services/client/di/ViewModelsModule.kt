package com.klima7.services.client.di

import com.klima7.services.client.features.splash.SplashViewModel
import com.klima7.services.common.components.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { SplashViewModel(get()) }

}
