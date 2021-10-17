package com.klima7.services.expert.di

import com.klima7.services.expert.features.setup.GetCurrentExpertSetupStateUC
import com.klima7.services.expert.common.domain.usecases.GetCurrentExpertUC
import com.klima7.services.expert.features.splash.GetCurrentExpertStateUC
import org.koin.dsl.module

val useCasesExpertModule = module {

    single { GetCurrentExpertUC(get(), get()) }
    single { GetCurrentExpertSetupStateUC(get(), get()) }
    single { GetCurrentExpertStateUC(get(), get()) }

}
