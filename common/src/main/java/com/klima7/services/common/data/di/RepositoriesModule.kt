package com.klima7.services.common.data.di

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.data.repositories.ServicesRepository
import org.koin.dsl.module

val reposModule = module {

    single { ExpertsRepository(get(), get(), get(), get()) }
    single { AuthRepository(get()) }
    single { ServicesRepository(get()) }
    single { RatingsRepository(get(), get()) }

}
