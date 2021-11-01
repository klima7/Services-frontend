package com.klima7.services.expert.di

import com.klima7.services.expert.features.area.SetCurrentExpertWorkingAreaUC
import com.klima7.services.expert.features.delete.DeleteExpertUC
import com.klima7.services.expert.features.info.SetCurrentExpertInfoAndImageUC
import com.klima7.services.expert.features.jobs.base.GetJobsUC
import com.klima7.services.expert.features.jobs.new.GetNewJobsIdsUC
import com.klima7.services.expert.features.jobs.rejected.GetRejectedJobsIdsUC
import com.klima7.services.expert.features.services.GetCategorisedAndMarkedServices
import com.klima7.services.expert.features.services.SetCurrentExpertServices
import com.klima7.services.expert.features.setup.GetCurrentExpertSetupStateUC
import com.klima7.services.expert.features.splash.GetCurrentExpertStateUC
import com.klima7.services.expert.usecases.GetCurrentExpertUC
import com.klima7.services.expert.usecases.RejectJobUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetCurrentExpertUC(get(), get()) }
    single { GetCurrentExpertSetupStateUC(get()) }
    single { GetCurrentExpertStateUC(get(), get()) }
    single { SetCurrentExpertServices(get()) }
    single { SetCurrentExpertWorkingAreaUC(get()) }
    single { GetCategorisedAndMarkedServices(get(), get()) }
    single { SetCurrentExpertInfoAndImageUC(get()) }
    single { DeleteExpertUC(get(), get()) }
    single { GetJobsUC(get()) }
    single { GetNewJobsIdsUC(get()) }
    single { GetRejectedJobsIdsUC(get()) }
    single { RejectJobUC(get()) }

}
