package com.klima7.services.client.di

import com.klima7.services.client.features.addrating.AddRatingUC
import com.klima7.services.client.features.addrating.GetOfferWithExpertUC
import com.klima7.services.client.features.delete.DeleteClientUC
import com.klima7.services.client.features.info.SetCurrentClientInfoUC
import com.klima7.services.client.features.jobs.GetCurrentClientJobsUC
import com.klima7.services.client.features.jobsetup.details.CreateJobUC
import com.klima7.services.client.features.jobsetup.location.AddLastLocationUC
import com.klima7.services.client.features.jobsetup.location.GetLastLocationsUC
import com.klima7.services.client.features.jobsetup.service.GetServicesFromCategoryUC
import com.klima7.services.client.features.login.CompleteClientLoginUC
import com.klima7.services.client.features.newjob.GetAllCategoriesUC
import com.klima7.services.client.features.offers.FinishJobUC
import com.klima7.services.client.features.offers.GetOffersWithExpertForJobUC
import com.klima7.services.client.features.setup.GetCurrentClientSetupStateUC
import com.klima7.services.client.features.splash.GetCurrentClientStateUC
import com.klima7.services.client.usecases.GetCurrentClientUC
import com.klima7.services.client.usecases.GetJobUC
import com.klima7.services.client.usecases.SignOutClientUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetCurrentClientStateUC(get(), get()) }
    single { GetCurrentClientUC(get(), get()) }
    single { GetCurrentClientSetupStateUC(get()) }
    single { SetCurrentClientInfoUC(get()) }
    single { DeleteClientUC(get(), get()) }
    single { GetCurrentClientJobsUC(get(), get()) }
    single { GetJobUC(get()) }
    single { GetOffersWithExpertForJobUC(get(), get()) }
    single { FinishJobUC(get()) }
    single { GetOfferWithExpertUC(get(), get()) }
    single { AddRatingUC(get()) }
    single { GetAllCategoriesUC(get()) }
    single { GetServicesFromCategoryUC(get()) }
    single { AddLastLocationUC(get()) }
    single { GetLastLocationsUC(get()) }
    single { CreateJobUC(get()) }
    single { SignOutClientUC(get(), get(), get()) }
    single { CompleteClientLoginUC(get(), get()) }

}
