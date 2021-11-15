package com.klima7.services.common.di

import com.klima7.services.common.components.comments.GetRatingsForExpertUC
import com.klima7.services.common.components.msgsend.SendImageMessageUC
import com.klima7.services.common.components.msgsend.SendTextMessageUC
import com.klima7.services.common.components.profile.services.GetServicesFromIds
import com.klima7.services.common.usecases.SignOutUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetServicesFromIds(get()) }
    single { GetRatingsForExpertUC(get()) }
    single { SignOutUC(get()) }
    single { SendTextMessageUC(get()) }
    single { SendImageMessageUC(get()) }

}
