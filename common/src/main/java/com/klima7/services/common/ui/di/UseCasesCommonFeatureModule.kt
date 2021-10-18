package com.klima7.services.common.ui.di

import com.klima7.services.common.domain.usecases.SignOutUC
import com.klima7.services.common.ui.profile.GetExpertUC
import org.koin.dsl.module

val useCasesCommonFeatureModule = module {

    single { GetExpertUC(get()) }

}
