package com.klima7.services.common.data.di

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import org.koin.dsl.module

val reposModule = module {

    single { ExpertsRepository(get(), get()) }
    single { AuthRepository(get()) }

}
