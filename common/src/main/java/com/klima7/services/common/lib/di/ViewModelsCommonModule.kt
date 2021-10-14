package com.klima7.services.common.lib.di

import com.klima7.services.common.lib.faildialog.FailureDialogViewModel
import com.klima7.services.common.lib.failurable.FailurableWrapperViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsCommonModule = module {

    viewModel { FailurableWrapperViewModel() }
    viewModel { FailureDialogViewModel() }

}
