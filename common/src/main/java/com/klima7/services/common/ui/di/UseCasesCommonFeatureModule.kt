package com.klima7.services.common.ui.di

import com.klima7.services.common.ui.comments.GetRatingsForExpertUC
import com.klima7.services.common.ui.profile.GetExpertUC
import com.klima7.services.common.ui.profile.services.GetServicesFromIds
import org.koin.dsl.module

val useCasesCommonFeatureModule = module {

    single { GetExpertUC(get()) }
    single { GetServicesFromIds(get()) }
    single { GetRatingsForExpertUC(get()) }

}
