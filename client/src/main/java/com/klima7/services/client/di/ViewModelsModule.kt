package com.klima7.services.client.di

import com.klima7.services.client.features.info.InfoViewModel
import com.klima7.services.client.features.setup.SetupViewModel
import com.klima7.services.client.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { SetupViewModel(get()) }
    viewModel { InfoViewModel(get(), get()) }

}
