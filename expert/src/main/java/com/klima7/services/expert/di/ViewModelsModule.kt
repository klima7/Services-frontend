package com.klima7.services.expert.di

import com.klima7.services.expert.features.area.WorkingAreaViewModel
import com.klima7.services.expert.features.delete.DeleteViewModel
import com.klima7.services.expert.features.home.HomeViewModel
import com.klima7.services.expert.features.info.InfoViewModel
import com.klima7.services.expert.features.job.JobViewModel
import com.klima7.services.expert.features.jobs.JobsViewModel
import com.klima7.services.expert.features.jobs.new.NewJobsListViewModel
import com.klima7.services.expert.features.jobs.rejected.RejectedJobsListViewModel
import com.klima7.services.expert.features.login.LoginViewModel
import com.klima7.services.expert.features.offer.OfferViewModel
import com.klima7.services.expert.features.offers.OffersViewModel
import com.klima7.services.expert.features.offers.archive.ArchiveOffersListViewModel
import com.klima7.services.expert.features.offers.current.CurrentOffersListViewModel
import com.klima7.services.expert.features.profile.ProfileViewModel
import com.klima7.services.expert.features.services.ServicesViewModel
import com.klima7.services.expert.features.settings.SettingsViewModel
import com.klima7.services.expert.features.setup.SetupViewModel
import com.klima7.services.expert.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { SetupViewModel(get(), get()) }
    viewModel { InfoViewModel(get(), get()) }
    viewModel { ServicesViewModel(get(), get()) }
    viewModel { WorkingAreaViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { DeleteViewModel(get()) }
    viewModel { JobsViewModel() }
    viewModel { NewJobsListViewModel(get(), get(), get(), get()) }
    viewModel { RejectedJobsListViewModel(get(), get(), get()) }
    viewModel { JobViewModel(get(), get(), get()) }
    viewModel { OffersViewModel() }
    viewModel { CurrentOffersListViewModel(get(), get(), get()) }
    viewModel { ArchiveOffersListViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { OfferViewModel(get(), get(), get(), get()) }
    viewModel { LoginViewModel(get()) }

}
