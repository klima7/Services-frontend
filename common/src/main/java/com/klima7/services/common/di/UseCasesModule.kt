package com.klima7.services.common.di

import com.klima7.services.common.components.msgsend.SendImageMessageUC
import com.klima7.services.common.components.msgsend.SendTextMessageUC
import com.klima7.services.common.components.msgviewer.GetConverserReadTimeUC
import com.klima7.services.common.components.msgviewer.GetMessagesFlowUC
import com.klima7.services.common.components.offer.GetOfferStreamUC
import com.klima7.services.common.components.offer.SetOfferStatusUC
import com.klima7.services.common.components.profile.services.GetServicesFromIds
import com.klima7.services.common.components.rating.GetRatingWithExpertUC
import com.klima7.services.common.components.ratings.GetRatingsForExpertUC
import com.klima7.services.common.usecases.GetExpertUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetServicesFromIds(get()) }
    single { GetRatingsForExpertUC(get()) }
    single { SendTextMessageUC(get()) }
    single { SendImageMessageUC(get()) }
    single { GetRatingWithExpertUC(get(), get()) }
    single { GetExpertUC(get()) }
    single { SetOfferStatusUC(get()) }
    single { GetOfferStreamUC(get()) }
    single { GetMessagesFlowUC(get()) }
    single { GetConverserReadTimeUC(get()) }

}
