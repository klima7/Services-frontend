package com.klima7.services.expert.di

import com.klima7.services.expert.ExpertNavigator
import com.klima7.services.expert.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val toolsModule = module {

    single { ExpertNavigator() }

}
