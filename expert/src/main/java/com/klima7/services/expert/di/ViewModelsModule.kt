package com.klima7.services.expert.di

import com.klima7.services.expert.features.area.WorkingAreaViewModel
import com.klima7.services.expert.features.delete.DeleteViewModel
import com.klima7.services.expert.features.home.HomeViewModel
import com.klima7.services.expert.features.info.InfoViewModel
import com.klima7.services.expert.features.jobs.JobsViewModel
import com.klima7.services.expert.features.services.ServicesViewModel
import com.klima7.services.expert.features.services.category.ServicesCategoryViewModel
import com.klima7.services.expert.features.services.multicategory.ServicesMultiCategoryViewModel
import com.klima7.services.expert.features.settings.SettingsViewModel
import com.klima7.services.expert.features.setup.SetupViewModel
import com.klima7.services.expert.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { SetupViewModel(get()) }
    viewModel { InfoViewModel(get(), get()) }
    viewModel { ServicesViewModel(get(), get()) }
    viewModel { WorkingAreaViewModel(get(), get()) }
    viewModel { ServicesCategoryViewModel() }
    viewModel { ServicesMultiCategoryViewModel() }
    viewModel { SettingsViewModel(get()) }
    viewModel { DeleteViewModel(get()) }
    viewModel { JobsViewModel(get(), get()) }

}
