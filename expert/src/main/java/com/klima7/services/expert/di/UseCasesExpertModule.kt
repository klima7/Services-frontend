package com.klima7.services.expert.di

import com.klima7.services.expert.features.setup.GetCurrentExpertSetupStateUC
import com.klima7.services.expert.common.domain.usecases.GetCurrentExpertUC
import com.klima7.services.expert.features.area.SetCurrentExpertWorkingAreaUC
import com.klima7.services.expert.features.services.GetCategorisedAndMarkedServices
import com.klima7.services.expert.features.services.SetCurrentExpertServices
import com.klima7.services.expert.features.splash.GetCurrentExpertStateUC
import org.koin.dsl.module

val useCasesExpertModule = module {

    single { GetCurrentExpertUC(get(), get()) }
    single { GetCurrentExpertSetupStateUC(get()) }
    single { GetCurrentExpertStateUC(get(), get()) }
    single { SetCurrentExpertServices(get()) }
    single { SetCurrentExpertWorkingAreaUC(get()) }
    single { GetCategorisedAndMarkedServices(get(), get(), get()) }

}
