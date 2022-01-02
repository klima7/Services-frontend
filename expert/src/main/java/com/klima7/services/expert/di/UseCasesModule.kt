package com.klima7.services.expert.di

import com.klima7.services.expert.features.area.SetCurrentExpertWorkingAreaUC
import com.klima7.services.expert.features.delete.DeleteExpertUC
import com.klima7.services.expert.features.info.SetCurrentExpertInfoAndImageUC
import com.klima7.services.expert.features.job.AcceptJobUC
import com.klima7.services.expert.features.job.GetCurrentExpertJobUC
import com.klima7.services.expert.features.jobs.base.GetCurrentExpertJobsUC
import com.klima7.services.expert.features.jobs.new.GetNewJobsIdsUC
import com.klima7.services.expert.features.jobs.rejected.GetRejectedJobsIdsUC
import com.klima7.services.expert.features.login.CompleteExpertLoginUC
import com.klima7.services.expert.features.offer.GetClientUC
import com.klima7.services.expert.features.offers.archive.GetArchiveOffersForCurrentExpertUC
import com.klima7.services.expert.features.offers.current.GetCurrentOffersForCurrentExpertUC
import com.klima7.services.expert.features.services.GetAllAndSelectedServicesUC
import com.klima7.services.expert.features.services.SetCurrentExpertServicesUC
import com.klima7.services.expert.features.setup.GetCurrentExpertSetupStateUC
import com.klima7.services.expert.features.splash.GetCurrentExpertStateUC
import com.klima7.services.expert.usecases.*
import org.koin.dsl.module

val useCasesModule = module {

    single { GetCurrentExpertUC(get(), get()) }
    single { GetCurrentExpertSetupStateUC(get()) }
    single { GetCurrentExpertStateUC(get(), get()) }
    single { SetCurrentExpertServicesUC(get()) }
    single { SetCurrentExpertWorkingAreaUC(get()) }
    single { SetCurrentExpertInfoAndImageUC(get()) }
    single { DeleteExpertUC(get(), get()) }
    single { GetCurrentExpertJobsUC(get()) }
    single { GetNewJobsIdsUC(get(), get()) }
    single { GetRejectedJobsIdsUC(get(), get()) }
    single { RejectJobUC(get()) }
    single { GetCurrentExpertJobUC(get(), get(), get()) }
    single { AcceptJobUC(get()) }
    single { GetCurrentOffersForCurrentExpertUC(get(), get()) }
    single { GetArchiveOffersForCurrentExpertUC(get(), get()) }
    single { SetOfferArchivedUC(get()) }
    single { GetClientUC(get()) }
    single { GetAllAndSelectedServicesUC(get(), get()) }
    single { SignOutExpertUC(get(), get()) }
    single { CompleteExpertLoginUC(get()) }
    single { GetCurrentExpertServicesUC(get(), get()) }

}
