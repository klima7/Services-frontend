package com.klima7.services.client.di

import com.klima7.services.client.features.splash.GetCurrentExpertStateUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetCurrentExpertStateUC(get(), get()) }

}
