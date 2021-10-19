package com.klima7.services.common.ui.di

import com.klima7.services.common.ui.faildialog.FailureDialogViewModel
import com.klima7.services.common.ui.loadable.LoadableWrapperViewModel
import com.klima7.services.common.ui.profile.ProfileViewModel
import com.klima7.services.common.ui.profile.services.ProfileServicesContentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsCommonModule = module {

    viewModel { LoadableWrapperViewModel() }
    viewModel { FailureDialogViewModel() }
    viewModel { ProfileViewModel(get()) }
    viewModel { ProfileServicesContentViewModel(get()) }

}
