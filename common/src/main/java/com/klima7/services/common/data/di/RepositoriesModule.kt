package com.klima7.services.common.data.di

import com.klima7.services.common.data.repositories.*
import org.koin.dsl.module

val reposModule = module {

    single { ExpertsRepository(get()) }
    single { ClientsRepository(get()) }
    single { AuthRepository(get()) }
    single { ServicesRepository(get()) }
    single { RatingsRepository(get()) }
    single { JobsRepository(get()) }
    single { JobsStatusRepository(get()) }
    single { OffersRepository(get()) }
    single { MessagesRepository(get()) }
    single { LastLocationsRepository(get()) }

}
