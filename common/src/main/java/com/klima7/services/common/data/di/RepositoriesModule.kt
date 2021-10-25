package com.klima7.services.common.data.di

import com.klima7.services.common.data.repositories.*
import org.koin.dsl.module

val reposModule = module {

    single { ExpertsRepository(get(), get(), get(), get()) }
    single { ClientsRepository(get(), get()) }
    single { AuthRepository(get()) }
    single { ServicesRepository(get()) }
    single { RatingsRepository(get(), get()) }

}
