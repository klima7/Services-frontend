package com.klima7.services.common.ui.di

import com.klima7.services.common.ui.faildialog.FailureDialogViewModel
import com.klima7.services.common.ui.loadable.LoadableWrapperViewModel
import com.klima7.services.common.ui.profile.ProfileContentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsCommonModule = module {

    viewModel { LoadableWrapperViewModel() }
    viewModel { FailureDialogViewModel() }
    viewModel { ProfileContentViewModel() }

}
