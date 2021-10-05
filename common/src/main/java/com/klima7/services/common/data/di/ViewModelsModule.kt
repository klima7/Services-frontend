package com.klima7.services.common.data.di

import com.klima7.services.common.lib.fragments.FailurableWrapperViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonViewModelsModule = module {

    viewModel { FailurableWrapperViewModel() }

}
