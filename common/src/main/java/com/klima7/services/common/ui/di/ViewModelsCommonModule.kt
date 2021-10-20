package com.klima7.services.common.ui.di

import com.klima7.services.common.ui.areavis.AreaVisualizationViewModel
import com.klima7.services.common.ui.faildialog.FailureDialogViewModel
import com.klima7.services.common.ui.profile.ProfileViewModel
import com.klima7.services.common.ui.profile.services.ProfileServicesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsCommonModule = module {

    viewModel { FailureDialogViewModel() }
    viewModel { ProfileViewModel(get()) }
    viewModel { ProfileServicesViewModel(get()) }
    viewModel { AreaVisualizationViewModel() }

}
