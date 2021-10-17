package com.klima7.services.common.domain.di

import com.klima7.services.common.domain.usecases.SignOutUC
import org.koin.dsl.module

val useCasesCommonModule = module {

    single { SignOutUC(get()) }

}
