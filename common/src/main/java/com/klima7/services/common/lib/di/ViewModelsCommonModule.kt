package com.klima7.services.common.lib.di

import com.klima7.services.common.lib.avatar.AvatarViewModel
import com.klima7.services.common.lib.faildialog.FailureDialogViewModel
import com.klima7.services.common.lib.loadable.LoadableWrapperViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsCommonModule = module {

    viewModel { LoadableWrapperViewModel() }
    viewModel { FailureDialogViewModel() }
    viewModel { AvatarViewModel() }

}
