package com.klima7.services.common.di

import com.klima7.services.common.components.comments.GetRatingsForExpertUC
import com.klima7.services.common.components.profile.GetExpertUC
import com.klima7.services.common.components.profile.services.GetServicesFromIds
import com.klima7.services.common.usecases.GetJobUC
import com.klima7.services.common.usecases.SignOutUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetExpertUC(get()) }
    single { GetServicesFromIds(get()) }
    single { GetRatingsForExpertUC(get()) }
    single { SignOutUC(get()) }
    single { GetJobUC(get()) }

}
