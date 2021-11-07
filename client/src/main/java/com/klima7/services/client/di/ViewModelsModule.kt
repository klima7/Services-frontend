package com.klima7.services.client.di

import com.klima7.services.client.features.delete.DeleteViewModel
import com.klima7.services.client.features.home.HomeViewModel
import com.klima7.services.client.features.info.InfoViewModel
import com.klima7.services.client.features.job.JobViewModel
import com.klima7.services.client.features.jobs.JobsViewModel
import com.klima7.services.client.features.offers.OffersViewModel
import com.klima7.services.client.features.settings.SettingsViewModel
import com.klima7.services.client.features.setup.SetupViewModel
import com.klima7.services.client.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { SetupViewModel(get()) }
    viewModel { InfoViewModel(get(), get()) }
    viewModel { HomeViewModel() }
    viewModel { DeleteViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { JobsViewModel(get()) }
    viewModel { OffersViewModel(get(), get()) }
    viewModel { JobViewModel(get(), get()) }

}
