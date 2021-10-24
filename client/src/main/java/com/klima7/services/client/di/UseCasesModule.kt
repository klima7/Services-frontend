package com.klima7.services.client.di

import com.klima7.services.client.features.splash.GetCurrentClientStateUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetCurrentClientStateUC(get(), get()) }

}
