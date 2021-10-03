package com.klima7.services.common.data.di

import com.klima7.services.common.data.repositories.ExpertsRepository
import org.koin.dsl.module

val reposModule = module {

    single { ExpertsRepository(get()) }

}
