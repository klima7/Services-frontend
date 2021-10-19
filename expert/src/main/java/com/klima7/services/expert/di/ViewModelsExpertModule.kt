package com.klima7.services.expert.di

import com.klima7.services.expert.features.area.WorkingAreaContentViewModel
import com.klima7.services.expert.features.home.HomeViewModel
import com.klima7.services.expert.features.info.InfoViewModel
import com.klima7.services.expert.features.login.LoginViewModel
import com.klima7.services.expert.features.services.ServicesContentViewModel
import com.klima7.services.expert.features.services.category.ServicesCategoryViewModel
import com.klima7.services.expert.features.services.multicategory.ServicesMultiCategoryViewModel
import com.klima7.services.expert.features.settings.SettingsViewModel
import com.klima7.services.expert.features.setup.SetupViewModel
import com.klima7.services.expert.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsExpertModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SetupViewModel(get()) }
    viewModel { InfoViewModel(get(), get()) }
    viewModel { ServicesContentViewModel(get(), get()) }
    viewModel { WorkingAreaContentViewModel(get(), get()) }
    viewModel { ServicesCategoryViewModel() }
    viewModel { ServicesMultiCategoryViewModel() }
    viewModel { SettingsViewModel(get()) }

}
